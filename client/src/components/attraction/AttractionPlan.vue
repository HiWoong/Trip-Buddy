<script setup>
import http from "@/util/http-common.js";
import { ref, watch, onMounted } from "vue";
import { VueDraggableNext } from "vue-draggable-next";
import { useRouter } from "vue-router";

// cookies
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();
const router = useRouter();

var map;
const keyword = ref("역삼역");
const markers = ref([]);
const result = ref([]);
const idx = ref(0);
const nowDay = ref(2);
onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    console.log("loadMap");
    loadMap();
  } else {
    console.log("loadScript");
    loadScript();
  }
  let resultList = document.getElementById("firstDay");
  let lists = document.createElement("div");
  lists.className = "nowDay";
  lists.innerHTML = 1 + "일차";
  resultList.appendChild(lists);
  nowDay.value = 2;
});

watch(result.value, () => {
  let resultList = document.getElementById("draggable");
  resultList.innerHTML = "";
  result.value.forEach((data) => {
    if (data.nowDay == null) {
      let lists = document.createElement("div");
      lists.className = "pickPlace";

      let place_name = document.createElement("div");
      place_name.innerHTML = "도착지: " + data.place_name;
      place_name.className = "pickPlaceName";

      let road_address_name = document.createElement("div");
      road_address_name.innerHTML = data.road_address_name;

      let address_name = document.createElement("div");
      address_name.innerHTML = data.address_name;
      lists.appendChild(place_name);
      lists.appendChild(road_address_name);
      lists.appendChild(address_name);

      if (data.phone != "" && data.phone != null) {
        let phone = document.createElement("div");
        phone.innerHTML = data.phone;
        lists.appendChild(phone);
      }

      let removePick = document.createElement("button");
      removePick.className = "removePick";
      removePick.innerHTML = "삭제";
      removePick.onclick = () => {
        removePickPlace(data.address_name, data.index);
      };
      lists.appendChild(removePick);
      resultList.appendChild(lists);
    } else {
      let lists = document.createElement("div");
      lists.className = "nowDay";
      lists.innerHTML = data.nowDay + "일차";
      lists.ondblclick = () => {
        removeNowDay(data.nowDay);
      };
      resultList.appendChild(lists);
    }
  });
  console.log(result.value);
});

const removeNowDay = (target) => {
  if (target != nowDay.value - 1) {
    alert("가장 뒤 날짜부터 제거해야 합니다.");
  } else {
    for (let i = 0; i < result.value.length; i++) {
      if (result.value[i].nowDay == target) {
        result.value.splice(i, 1);
        break;
      }
    }
    nowDay.value--;
  }
};

const addPickPlace = (places) => {
  result.value.push({
    place_name: places.place_name,
    road_address_name: places.road_address_name,
    address_name: places.address_name,
    phone: places.phone,
    // index: idx.value++,
    index: (idx.value++).toString(),
    //
  });
};

const removePickPlace = (address_name, index) => {
  for (let i = 0; i < result.value.length; i++) {
    console.log(result.value);
    if (
      result.value[i].address_name == address_name &&
      // result.value[i].index == index
      Number(result.value[i].index) == index
    ) {
      result.value.splice(i, 1);
      break;
    }
  }
  console.log(result.value);
};

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

function searchPlaces() {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);
  const mapTypeControl = new kakao.maps.MapTypeControl();

  map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

  const zoomControl = new kakao.maps.ZoomControl();
  map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

  const ps = new kakao.maps.services.Places(map);
  const infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
  if (!keyword.value.replace(/^\s+|\s+$/g, "")) {
    alert("키워드를 입력해주세요!");
    return;
  }
  ps.keywordSearch(keyword.value, (data, status, pagination) => {
    if (status === kakao.maps.services.Status.OK) {
      // 정상적으로 검색이 완료됐으면
      // 검색 목록과 마커를 표출합니다
      displayPlaces(data);

      // 페이지 번호를 표출합니다
      displayPagination(pagination);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
      alert("검색 결과가 존재하지 않습니다.");
      return;
    } else if (status === kakao.maps.services.Status.ERROR) {
      alert("검색 결과 중 오류가 발생했습니다.");
      return;
    }
  });
  function displayPlaces(places) {
    let listEl = document.getElementById("placesList");
    let menuEl = document.getElementById("menu_wrap");
    let fragment = document.createDocumentFragment();

    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);

    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();
    map.setCenter(new kakao.maps.LatLng(places[0].y, places[0].x));
    for (var i = 0; i < places.length; i++) {
      // 마커를 생성하고 지도에 표시합니다
      const placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
      const marker = addMarker(placePosition, i);
      const itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다
      console.log(places[i]);

      // 마커와 검색결과 항목에 mouseover 했을때
      // 해당 장소에 인포윈도우에 장소명을 표시합니다
      // mouseout 했을 때는 인포윈도우를 닫습니다
      (function (marker, place_name, road_address_name, phone, placePosition) {
        kakao.maps.event.addListener(marker, "click", function () {
          displayInfowindow(marker, place_name, road_address_name, phone);
        });

        kakao.maps.event.addListener(marker, "mouseenter", function () {
          infowindow.close();
        });

        itemEl.onclick = function () {
          displayInfowindow(marker, place_name, road_address_name, phone);

          map.panTo(placePosition);
        };

        itemEl.onmouseenter = function () {
          infowindow.close();
        };
      })(marker, places[i].place_name, places[i].road_address_name, places[i].phone, placePosition);

      fragment.appendChild(itemEl);
    }

    // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;
  }

  // 검색결과 항목을 Element로 반환하는 함수입니다
  function getListItem(index, places) {
    let el = document.createElement("li");

    let markerbg = document.createElement("span");
    markerbg.className = "markerbg marker_" + (index + 1);

    let info = document.createElement("div");
    info.className = "info";
    let place_name = document.createElement("h5");
    place_name.innerHTML = places.place_name;
    info.appendChild(place_name);
    if (places.road_address_name) {
      let road = document.createElement("span");
      road.innerHTML = places.road_address_name;
      let jibun = document.createElement("span");
      jibun.innerHTML = places.address_name;

      info.appendChild(road);
      info.appendChild(jibun);
    } else {
      let address = document.createElement("span");
      address.innerHTML = places.address_name;

      info.appendChild(address);
    }
    let tel = document.createElement("span");
    tel.className = "tel";
    tel.innerHTML = places.phone;
    info.appendChild(tel);

    let pick = document.createElement("button");
    pick.className = "pick";
    pick.innerHTML = "담기";
    pick.onclick = () => {
      addPickPlace(places);
    };
    info.appendChild(pick);
    el.appendChild(markerbg);
    el.appendChild(info);
    el.className = "item";

    return el;
  }

  // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
  function addMarker(position, idx) {
    const imageSrc =
      "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png"; // 마커 이미지 url, 스프라이트 이미지를 씁니다
    const imageSize = new kakao.maps.Size(36, 37); // 마커 이미지의 크기
    const imgOptions = {
      spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
      spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
      offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
    };
    const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions);
    const marker = new kakao.maps.Marker({
      position: position, // 마커의 위치
      image: markerImage,
    });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers.value.push(marker); // 배열에 생성된 마커를 추가합니다

    return marker;
  }

  // 지도 위에 표시되고 있는 마커를 모두 제거합니다
  function removeMarker() {
    markers.value.forEach((data) => data.setMap(null));
    markers.value = [];
  }

  // 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
  function displayPagination(pagination) {
    let paginationEl = document.getElementById("pagination");
    let fragment = document.createDocumentFragment();
    let i;

    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
      paginationEl.removeChild(paginationEl.lastChild);
    }

    for (i = 1; i <= pagination.last; i++) {
      let el = document.createElement("a");
      el.href = "#";
      el.innerHTML = i;

      if (i === pagination.current) {
        el.className = "on";
      } else {
        el.onclick = (function (i) {
          return function () {
            pagination.gotoPage(i);
          };
        })(i);
      }

      fragment.appendChild(el);
    }
    paginationEl.appendChild(fragment);
  }

  // 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
  // 인포윈도우에 장소명을 표시합니다
  function displayInfowindow(marker, place_name, road_address_name, phone) {
    let wrap = document.createElement("div");
    wrap.className = "wrap";

    let info = document.createElement("div");
    info.className = "info";

    let title = document.createElement("div");
    title.className = "title";
    if (place_name.length > 10) title.innerHTML = place_name.substring(0, 10) + "...";
    else title.innerHTML = place_name;

    let body = document.createElement("div");
    body.className = "body";

    let desc = document.createElement("div");
    desc.className = "desc";

    let ellipsis = document.createElement("div");
    ellipsis.className = "ellipsis";
    ellipsis.innerHTML = road_address_name;

    let jibun = document.createElement("div");
    jibun.className = "jibun ellipsis";
    jibun.innerText = phone;
    desc.appendChild(ellipsis);
    desc.appendChild(jibun);

    body.appendChild(desc);

    info.appendChild(title);
    info.appendChild(body);

    wrap.appendChild(info);

    infowindow.setContent(wrap);
    infowindow.open(map, marker);
  }

  // 검색결과 목록의 자식 Element를 제거하는 함수입니다
  function removeAllChildNods(el) {
    while (el.hasChildNodes()) {
      el.removeChild(el.lastChild);
    }
  }
}

const addDay = () => {
  if (nowDay.value == 6) {
    alert("여행 계획은 최대 5일까지만 가능합니다.");
  } else {
    result.value.push({
      nowDay: nowDay.value,
    });
    nowDay.value++;
  }
};

// onclick
const addPlan = () => {
  // 보낼 때 맨 앞에 {nowDay: 1}을 해줘야 함
  // http.post("url", result.value);

  const userId = cookies.get("userId");

  if (userId == null) {
    alert("여행 플래너를 이용하려면 로그인이 필요합니다.");
  } else if (result.value.length == 0) {
    alert("최소 하나 이상의 계획을 담아야 합니다.");
  } else {
    // const plans = ref([]);
    // const plans = Object.assign(result);
    let plans = new Array(5);
    for (let i = 0; i < plans.length; i++) {
      plans[i] = new Array();
    }

    // plans.value.add({
    //   nowDay : 1
    // })
    let temp = plans[0];
    for (let res of result.value) {
      if (res.nowDay === undefined) {
        // temp.value.push(res.value);
        // console.log("temp.value", temp.value);
        temp.push(res);
        console.log("temp : ", temp);
        // console.log(res.place_name);
      } else {
        temp = plans[res.nowDay - 1];
        console.log(res.nowDay);
      }
    }

    console.log(JSON.stringify(plans[0]));
    console.log(JSON.stringify(plans[1]));
    console.log(JSON.stringify(plans[2]));
    console.log(JSON.stringify(plans[3]));
    console.log(JSON.stringify(plans[4]));

    // const plans = JSON.parse(JSON.stringify(result));
    // plans.value.unshift({
    //   nowDay : 1
    // });
    // console.log("addPlan, result : ", result.value);
    http.post("planapi/create", {
      userId: cookies.get("userId"),
      subject: "hello",
      content1: JSON.stringify(plans[0]),
      content2: JSON.stringify(plans[1]),
      content3: JSON.stringify(plans[2]),
      content4: JSON.stringify(plans[3]),
      content5: JSON.stringify(plans[4]),
    });

    alert("등록이 완료되었습니다. 마이페이지에서 확인하세요!");

    router.push("/");
  }
};
</script>
<!-------------------------------------------------------------------------------------------------------------->
<!-------------------------------------------------------------------------------------------------------------->
<!-------------------------------------------------------------------------------------------------------------->
<template>
  <div id="contents">
    <div id="menu_wrap">
      <div class="option">
        <h5>검색하세요!</h5>
        <div>
          <input type="text" v-model="keyword" id="keyword" />
          <button id="searchButton" @click="searchPlaces" @keypress="() => searchPlaces()">
            검색하기
          </button>
        </div>
      </div>
      <hr />
      <ul id="placesList"></ul>
      <div id="pagination"></div>
    </div>
    <div id="map"></div>
    <div id="menu_plan">
      <div id="dayImgSection">
        <button style="border: none; background-color: rgb(252, 227, 118)">
          <img id="submitImg" src="@/assets/img/day.png" @click="addDay" />
        </button>
        <div style="font-weight: bold; font-size: 25px">여행 계획</div>
        <button style="border: none; background-color: rgb(252, 227, 118)">
          <!-- 여기에서 포스트하자! -->
          <img id="submitImg" src="@/assets/img/store.png" @click="addPlan" />
        </button>
      </div>
      <div id="selectPlaces">
        <div id="firstDay"></div>
        <VueDraggableNext id="draggable" class="dragArea" :list="result" :sort="true">
        </VueDraggableNext>
      </div>
    </div>
  </div>
</template>
<!-------------------------------------------------------------------------------------------------------------->
<!-------------------------------------------------------------------------------------------------------------->
<!-------------------------------------------------------------------------------------------------------------->
<style>
* {
  margin: 0;
  padding: 0;
  font-family: "NanumSquare", dotum, "돋움", sans-serif;
  /* font-family: dotum, "돋움", sans-serif; */
}
.map_wrap,
.map_wrap * {
  font-size: 12px;
}
#menu_wrap {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  width: 330px;
  margin: 10px 0 0px 10px;
  padding: 5px;
  overflow-y: auto;
  background: rgba(255, 255, 255, 0.7);
  z-index: 1;
  font-size: 12px;
  border-radius: 10px;
  flex: 4;
}
.bg_white {
  background: #fff;
}
#menu_wrap hr {
  display: block;
  height: 1px;
  border: 0;
  border-top: 2px solid #5f5f5f;
  margin: 3px 0;
}
#menu_wrap .option {
  text-align: left;
  padding-left: 0;
  font-family: "NanumSquare";
}
#menu_wrap .option p {
  margin: 10px 0;
}
#menu_wrap .option button {
  margin-left: 5px;
}
#placesList {
  padding-left: 0;
}
#placesList li {
  list-style: none;
}
#placesList .item {
  position: relative;
  border-bottom: 1px solid #888;
  overflow: hidden;
  cursor: pointer;
  min-height: 65px;
}
#placesList .item span {
  display: block;
  margin-top: 4px;
}
#placesList .item h5,
#placesList .item .info {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
#placesList .item .info {
  padding: 10px 0 10px 55px;
}
#placesList .info .gray {
  color: #8a8a8a;
}
#placesList .info .jibun {
  padding-left: 26px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png)
    no-repeat;
}
#placesList .info .tel {
  color: #009900;
}
#placesList .item .markerbg {
  float: left;
  position: absolute;
  width: 36px;
  height: 37px;
  margin: 10px 0 0 10px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png)
    no-repeat;
}
#placesList .item .marker_1 {
  background-position: 0 -10px;
}
#placesList .item .marker_2 {
  background-position: 0 -56px;
}
#placesList .item .marker_3 {
  background-position: 0 -102px;
}
#placesList .item .marker_4 {
  background-position: 0 -148px;
}
#placesList .item .marker_5 {
  background-position: 0 -194px;
}
#placesList .item .marker_6 {
  background-position: 0 -240px;
}
#placesList .item .marker_7 {
  background-position: 0 -286px;
}
#placesList .item .marker_8 {
  background-position: 0 -332px;
}
#placesList .item .marker_9 {
  background-position: 0 -378px;
}
#placesList .item .marker_10 {
  background-position: 0 -423px;
}
#placesList .item .marker_11 {
  background-position: 0 -470px;
}
#placesList .item .marker_12 {
  background-position: 0 -516px;
}
#placesList .item .marker_13 {
  background-position: 0 -562px;
}
#placesList .item .marker_14 {
  background-position: 0 -608px;
}
#placesList .item .marker_15 {
  background-position: 0 -654px;
}
#pagination {
  margin: 10px auto;
  text-align: center;
}
#pagination a {
  display: inline-block;
  margin-right: 10px;
}
#pagination .on {
  font-weight: bold;
  cursor: default;
  color: #777;
}

.wrap * {
  padding: 0;
  margin: 0;
  font-family: "NanumSquare";
  /* font-family: sans-serif; */
  font-weight: 400;
}
.wrap .info {
  width: 100%;
  height: 75px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.info .title {
  padding: 0 0 0 3px;
  min-width: 150px;
  width: 100%;
  height: 25px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 16px;
  font-weight: bold;
  position: relative;
}

.info .body {
  position: relative;
  overflow: hidden;
  padding: 0 10px 0 0;
}
.info .desc {
  position: relative;
  margin: 2px 0 0 3px;
  height: 75px;
}
.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.jibun {
  color: #009900;
  font-size: 15px;
}
#selectPlaces {
  overflow-y: auto;
}

#selectPlaces::-webkit-scrollbar {
  width: 10px; /* 스크롤바의 너비 */
}
#selectPlaces::-webkit-scrollbar-thumb {
  height: 10%;
  background: rgb(252, 227, 118); /* 스크롤바의 색상 */
  border-radius: 15px;
}
#selectPlaces::-webkit-scrollbar-track {
  background: rgba(233, 214, 161, 0.5); /*스크롤바 뒷 배경 색상*/
}
#menu_wrap::-webkit-scrollbar {
  width: 10px; /* 스크롤바의 너비 */
}
#menu_wrap::-webkit-scrollbar-thumb {
  height: 10%; /* 스크롤바의 길이 */
  background: rgb(252, 227, 118); /* 스크롤바의 색상 */
  border-radius: 15px;
}
#menu_wrap::-webkit-scrollbar-track {
  background: rgba(233, 214, 161, 0.5); /*스크롤바 뒷 배경 색상*/
}
#dayImgSection {
  height: 50px;
  background-color: rgb(252, 227, 118);
  display: flex;
  align-items: center;
  justify-content: space-between;
}
#submitImg {
  width: 35px;
  height: 35px;
  left: 0;
  margin: 0 10px;
}
#searchButton {
  transition: 0.5s;
  cursor: pointer;
  text-align: center;
  border: none;
  border-radius: 4px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
  width: 80px;
  height: 30px;
  float: right;
}
#searchButton:active,
#searchButton:hover,
#searchButton:focus {
  background: #ccc;
  outline: 0;
}
@font-face {
  font-family: "NanumSquare";
  src: url("../../assets/fonts/NanumSquareR.ttf") format("truetype");
}
@font-face {
  font-family: "NanumSquareB";
  src: url("../../assets/fonts/NanumSquareB.ttf") format("truetype");
}
#keyword {
  width: 70%;
  height: 30px;
  font-size: 16px;
}
#contents {
  width: 100%;
  height: 850px;
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}
#map {
  /* width: 1275px;
    height: 905px; */
  height: 100%;
  margin: 10px 0 0 350px;
  flex: 2;
  border-radius: 10px;
}
#menu_plan {
  margin: 10px 0 0 10px;
  width: 310px;
  height: 100%;
  /* height: 904px; */
  background-color: ghostwhite;
  text-align: center;
  font-family: "NanumSquareB";
  /* font-family: sans-serif; */
  flex: 0.5;
  border: 2px solid gray;
  border-radius: 5px;
  display: flex;
  flex-direction: column;
}

.pick {
  float: right;
  border: 3px solid #77af9c;
  color: darkgray;
  box-sizing: border-box;
  padding: 5px 15px;
  border-radius: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 800;
  transition: 0.25s;
}
.pick:hover {
  background-color: #77af9c;
  color: #d7fff1;
}
.pickPlace {
  margin: 5px 10px 10px 10px;
  padding: 10px 10px;
  text-align: start;
  border: 2px dotted black;
  border-radius: 10px;
}

.pickPlaceName {
  /* text-align: center; */
  font-size: 20px;
  font-weight: bold;
  font-family: "NanumSquareB";
  color: steelblue;
}

.removePick {
  border: 2px solid #ff823a;
  color: darkgray;
  box-sizing: border-box;
  padding: 2px 8px;
  border-radius: 7px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 800;
  transition: 0.25s;
  width: 100%;
}
.removePick:hover {
  background-color: #ff823a;
  color: #000a07;
}
input {
  border: 1px solid gray;
  border-radius: 5px;
  padding: 0 0 0 4px;
}
.nowDay {
  height: 27px;
  border-radius: 10px;
  background-color: rgba(0, 0, 0, 0.2);
  margin: 2px 0 0 0;
  align-items: center;
}
</style>
