<script setup>
import http from "@/util/http-common.js";
import { ref, watch, onMounted } from "vue";
var map;
const keyword = ref("역삼역");
const markers = ref([]);
onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    console.log("loadMap");
    loadMap();
  } else {
    console.log("loadScript");
    loadScript();
  }
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
    pick.className = "pick btn btn-success";
    pick.innerHTML = "담기";
    pick.onclick = () => {
      alert(index + 1);
    };
    info.appendChild(pick);
    // console.log(markerbg + info);
    // el.innerHTML = markerbg + info;
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
</script>
<!-------------------------------------------------------------------------------------------------------------->
<!-------------------------------------------------------------------------------------------------------------->
<!-------------------------------------------------------------------------------------------------------------->
<template>
  <div id="contents" class="mt-3 pt-2">
    <div class="container mt-4">
      <div id="map"></div>
    </div>
    <div id="menu_plan">
      <h3>여행계획</h3>
      <div id="selectPlaces"></div>
    </div>
  </div>
  <div id="menu_wrap" class="bg_white">
    <div class="option">
      <h5>^__^ 검색해라</h5>
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
</template>
<!-------------------------------------------------------------------------------------------------------------->
<!-------------------------------------------------------------------------------------------------------------->
<!-------------------------------------------------------------------------------------------------------------->
<style>
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  /* font-family: "NanumSquare", dotum, "돋움", sans-serif; */
  font-family: dotum, "돋움", sans-serif;
  font-size: 12px;
}
#menu_wrap {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  width: 330px;
  margin: 60px 0 30px 10px;
  padding: 5px;
  overflow-y: auto;
  background: rgba(255, 255, 255, 0.7);
  z-index: 1;
  font-size: 12px;
  border-radius: 10px;
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
  /* font-family: "NanumSquare"; */
  font-family: sans-serif;
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
/* @font-face {
  font-family: "NanumSquare";
  src: url("../../assets/fonts/NanumSquareR.ttf") format("truetype");
}
@font-face {
  font-family: "NanumSquareB";
  src: url("../../assets/fonts/NanumSquareB.ttf") format("truetype");
} */
#keyword {
  width: 70%;
  height: 30px;
  font-size: 16px;
}
#contents {
  width: 100%;
  display: flex;
}
#map {
  width: 1275px;
  height: 905px;
  margin: 10px 0 0 190px;
}
#menu_plan {
  margin: 35px 0 0 20px;
  width: 300px;
  height: 904px;
  background-color: antiquewhite;
  text-align: center;
  /* font-family: "NanumSquareB"; */
  font-family: sans-serif;
}
.pick {
  float: right;
}
</style>
