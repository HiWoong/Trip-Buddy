<script setup>
import { onMounted, ref } from "vue";
import { useAttractionStore } from "@/stores/attractionStore.js";
import { useRouter } from "vue-router";
const props = defineProps({
  data: Object,
});
const attractionStore = useAttractionStore();
const { getLoadOn, setLoadOn } = attractionStore;
var map;
var polyline;
const router = useRouter();
const nowMarkers = ref([]);
const emit = defineEmits(["loadFind"]);

onMounted(async () => {
  if (window.kakao && window.kakao.maps) {
    console.log("AttractionLoad loadMap");
    // loadMap();
    loadMap();
  } else {
    console.log("AttractionLoad loadScript");
    loadScript();
  }
  // console.log(props.data);
  console.log("taxi", props.data.allData.summary.fare.taxi);
  console.log("duration", props.data.allData.summary.duration);
  console.log("distance", props.data.allData.summary.distance);

  nowMarkers.value.push(
    new kakao.maps.Marker({
      map: map,
      position: new kakao.maps.LatLng(props.data.dy, props.data.dx),
    })
  );
  nowMarkers.value.push(
    new kakao.maps.Marker({
      map: map,
      position: new kakao.maps.LatLng(props.data.ny, props.data.nx),
    })
  );
  polyline = new kakao.maps.Polyline({
    path: props.data.loads,
    strokeWeight: 4,
    strokeColor: "#44ADDA",
    strokeOpacity: 0.8,
    strokeStyle: "solid",
    endArrow: true,
  });

  polyline.setMap(map);
});
const loadMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(props.data.ny, props.data.nx),
    level: 5,
  };
  map = new kakao.maps.Map(container, options);
  var mapTypeControl = new kakao.maps.MapTypeControl();

  map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

  var zoomControl = new kakao.maps.ZoomControl();
  map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
};

const loadScript = () => {
  const script = document.createElement("script");
  script.src =
    "//dapi.kakao.com/v2/maps/sdk.js?appkey=" +
    import.meta.env.VITE_KAKAO_API_TOKEN +
    "&autoload=false&libraries=services,clusterer,drawing";
  script.onload = () => window.kakao.maps.load(() => loadMap());

  document.head.appendChild(script);
};

const closeLoad = () => {
  polyline.setMap(null);
  polyline = null;
  nowMarkers.value.forEach((data) => data.setMap(null));
  nowMarkers.value = [];
  setLoadOn();
};
let minute = Math.round(props.data.allData.summary.duration / 60);
let hour = Math.floor(minute / 60);
minute = hour > 0 ? minute % (hour * 60) : minute;
let second = props.data.allData.summary.duration % 60;
let totalDistance = (props.data.allData.summary.distance / 1000).toFixed(1);
console.log(hour, minute, second, totalDistance);
</script>

<template>
  <div class="loadMap">
    <div id="map" />
    <div class="loadContents">
      <div
        style="
          margin-left: 520px;
          width: 265px;
          height: 90px;
          background-color: white;
          display: flex;
          margin-top: 5px;
          background-color: mintcream;
          padding: 5px 0 0 10px;
          font-size: 18px;
          color: #292929;
          flex-direction: column;
          border: 2px solid gray;
          border-radius: 10px;
        "
      >
        <div>예상 택시 비용: {{ props.data.allData.summary.fare.taxi }}원</div>
        <div>약 {{ hour }}시간 {{ minute }}분 {{ second }}초 소요 예정</div>
        <div>총 거리 : {{ totalDistance }}km</div>
      </div>
      <div>
        <input class="loadCloseButton" type="button" value="닫기" @click="closeLoad" />
      </div>
    </div>
  </div>
</template>

<style scoped>
@font-face {
  font-family: "NanumSquare";
  src: url("../../assets/fonts/NanumSquareR.ttf") format("truetype");
}
@font-face {
  font-family: "NanumSquareB";
  src: url("../../assets/fonts/NanumSquareB.ttf") format("truetype");
}
* {
  margin: 0;
  padding: 0;
  font-family: "NanumSquare";
}
#map {
  width: 500px;
  height: 500px;
  border: 2px solid gray;
  border-radius: 10px;
}
.loadContents {
  display: flex;
  flex-direction: row;
  padding: 0 0 0 0;
}
.loadCloseButton {
  width: 60px;
  height: 89px;
  margin: 6px 0 0 5px;
  color: darkgray;
  border: 3px solid rgb(134, 193, 247);
  border-radius: 10px;
  font-size: 18px;
  box-sizing: border-box;
  box-shadow: 0 1px 2px rgb(134, 193, 247);
  transition: 0.25s;
  font-weight: 800;
}
.loadCloseButton:hover {
  background-color: rgb(134, 193, 247);
  color: #593f61;
}
</style>
