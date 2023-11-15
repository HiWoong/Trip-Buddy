<template>
  <div class="container mt-5 pt-5">
    <nav class="navbar navbar-expand-lg navbar-light bg-light shadow">
      <div class="container">
        <div class="alert alert-primary mt-3 text-center fw-bold" role="alert">
          전국 관광지 정보
        </div>
        <!-- 관광지 검색 start -->
        <form class="d-flex my-3" onsubmit="return false;" role="search" id="search-form">
          <select
            id="search-area"
            name="search-area"
            class="form-select me-2"
            v-model="searchOptions.area"
          >
            <option value="0" selected>선택해주세요</option>
          </select>
          <select
            id="search-content-id"
            name="search-content-id"
            class="form-select me-2"
            v-model="searchOptions.type"
          >
            <option value="0" selected>관광지 유형</option>
            <option value="12">관광지</option>
            <option value="14">문화시설</option>
            <option value="15">축제공연행사</option>
            <option value="25">여행코스</option>
            <option value="28">레포츠</option>
            <option value="32">숙박</option>
            <option value="38">쇼핑</option>
            <option value="39">음식점</option>
          </select>
          <input
            id="searchkeyword"
            name="searchkeyword"
            class="form-control me-2"
            type="search"
            placeholder="검색어"
            aria-label="검색어"
            v-model="searchOptions.word"
          />
          <button
            id="btn-search"
            class="btn btn-outline-success"
            type="button"
            @click="searchAttractions(searchOptions)"
          >
            검색
          </button>
        </form>
      </div>
    </nav>
    <div id="map" class="mt-3 pt-3" style="width: 100%; height: 530px"></div>
  </div>
  <div v-if="attractions[0] != null">
    <div class="container px-4 px-lg-5 mt-5">
      <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
        <div v-for="attraction in attractions" :key="attraction.title">
          <div class="col mb-5" style="height: 400px">
            <div class="card">
              <img
                class="card-img-top"
                :src="attraction.firstImage"
                style="width:268px; height: 200px; text-center"
              />
              <div class="card-body">
                <div class="text-center">
                  <h5 class="fw-bolder">{{ attraction.title }}</h5>
                  {{ attraction.addr1 }}
                </div>
              </div>
              <div class="card-footer">
                <div class="text-center">
                  <button
                    class="btn btn-outline-dark mt-auto"
                    @click="moveCenter(attraction.latitude, attraction.longitude)"
                  >
                    위치 확인
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div v-else>
    <div></div>
  </div>
</template>
<style>
#map {
  width: 100%;
  height: 400px;
}
.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}
.wrap .info {
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}
.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.info .close:hover {
  cursor: pointer;
}
.info .body {
  position: relative;
  overflow: hidden;
}
.info .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
}
.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}
.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}
.info:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.info .link {
  color: #5085bb;
}
</style>
<script setup>
import http from "@/util/http-common.js";
import { ref, watch, onMounted } from "vue";

var map;
// area, type은 필수
const searchOptions = ref({
  area: "",
  type: "0",
  word: "",
});

const attractions = ref([]);
const markers = ref([]);

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    attractions.value = [];
    loadMap();
  } else {
    loadScript();
  }
  http
    .get(
      "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
        import.meta.env.VITE_TRIP_SERVICE_KEY +
        "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json"
    )
    .then((data) => {
      console.log(data);
      let areas = data.data.response.body.items.item;
      let sel = document.getElementById("search-area");
      areas.forEach((area) => {
        let opt = document.createElement("option");
        opt.setAttribute("value", area.code);
        opt.appendChild(document.createTextNode(area.name));

        sel.appendChild(opt);
      });
    });
});

const loadMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
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

const searchAttractions = async (data) => {
  if (data.area == "" || data.type == "" || data.area == "0" || data.type == "0") {
    alert("지역과 관광지 유형은 필수 항목입니다.");
    return;
  }
  await http.get("/attractionapi/search", { params: data }).then(({ data }) => {
    attractions.value = data;
  });
  loadMarkers();
  // loadOverlay();
};
const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  // 마커 이미지를 생성합니다
  //   const imgSrc = require("@/assets/map/markerStar.png");
  // 마커 이미지의 이미지 크기 입니다
  //   const imgSize = new kakao.maps.Size(24, 35);
  //   const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

  // 마커를 생성합니다
  markers.value = [];
  attractions.value.forEach((data) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: new kakao.maps.LatLng(data.latitude, data.longitude), // 마커를 표시할 위치
      title: data.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      // clickable: true, // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지
    });

    // customoverlay 생성, 이때 map을 선언하지 않으면 지도위에 올라가지 않습니다.
    var overlay = new kakao.maps.CustomOverlay({
      position: new kakao.maps.LatLng(data.latitude, data.longitude),
    });
    var wrap = document.createElement("div");
    wrap.className = "wrap";

    var info = document.createElement("div");
    info.className = "info";

    var title = document.createElement("div");
    title.className = "title";
    title.innerHTML = data.title;

    var close = document.createElement("div");
    close.className = "close";
    close.title = "닫기";
    close.onclick = function () {
      overlay.setMap(null);
    };
    title.appendChild(close);

    var body = document.createElement("div");
    body.className = "body";

    var img = document.createElement("div");
    img.className = "img";

    var imgSrc = document.createElement("img");
    imgSrc.src = data.firstImage;
    imgSrc.width = "73";
    imgSrc.height = "70";
    img.appendChild(imgSrc);

    var desc = document.createElement("div");
    desc.className = "desc";

    var ellipsis = document.createElement("div");
    ellipsis.className = "ellipsis";
    ellipsis.innerHTML = data.addr1;

    var jibun = document.createElement("div");
    jibun.className = "jibun ellipsis";
    jibun.innerText = data.addr2;
    desc.appendChild(ellipsis);
    desc.appendChild(jibun);

    body.appendChild(img);
    body.appendChild(desc);

    info.appendChild(title);
    info.appendChild(body);

    wrap.appendChild(info);
    overlay.setContent(wrap);
    // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
    kakao.maps.event.addListener(marker, "click", function () {
      overlay.setMap(map);
    });
    markers.value.push(marker);
  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = attractions.value.reduce(
    (bounds, position) =>
      bounds.extend(new kakao.maps.LatLng(position.latitude, position.longitude)),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};

const moveCenter = (lat, lng) => {
  map.setCenter(new kakao.maps.LatLng(lat, lng));
  window.scrollTo({ top: 0, behavior: "smooth" });
};
</script>
