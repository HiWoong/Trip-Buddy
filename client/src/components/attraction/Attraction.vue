<template>
  <div class="container mt-5 pt-5">
    <nav class="navbar navbar-expand-lg navbar-light bg-light shadow">
      <div class="container">
        <div class="alert alert-primary mt-3 text-center fw-bold" role="alert">
          전국 관광지 정보
        </div>
        <!-- 관광지 검색 start -->
        <form class="d-flex my-3" onsubmit="return false;" role="search" method="POST" id="search-form" action="">
          <select id="search-area" name="search-area" class="form-select me-2">
            <option value="0" selected>선택해주세요</option>
          </select>
          <select id="search-content-id" name="search-content-id" class="form-select me-2">
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
            name = "searchkeyword"
            class="form-control me-2"
            type="search"
            placeholder="검색어"
            aria-label="검색어"
          />
          <button id="btn-search" class="btn btn-outline-success" type="button">검색</button>
        </form> 
        </div>
      </nav>
        <div id="map" class="mt-3 pt-3" style="width: 100%; height: 450px"></div>
    </div>
    <div v-if="attractions != null">
	<table>
		<tr><th>대표이미지</th><th>이름</th><th>주소</th><th>나와의거리</th></tr>
	<div v-for="attraction in attractions" :key="attraction.title">
		<tr>
			<td><img src=attraction.firstImage width="100px"></td>
			<td>attraction.title</td>
			<td>attraction.addr1</td>
			<td>attraction.distance</td>
		</tr>
	</div>
	</table>
</div>
<div v-else>
	<div>비어있음</div>
</div>



</template>
<style scoped>
#map {
  width: 100%;
  height: 400px;
}
</style>
<script>
import http from "@/util/http-common.js";
export default {
  name: "Attraction", // 컴포넌트 이름 지정
  data() {
    return {
      // map 객체 설정
      map: null,
    };
  },
  setup() {
  },
  created() {},
  mounted() {
    // api 스크립트 소스 불러오기 및 지도 출력
    if (window.kakao && window.kakao.maps) {
      this.loadMap();
    } else {
      this.loadScript();
    }
    http.get("https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
    import.meta.env.VITE_TRIP_SERVICE_KEY + "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json")
      .then((response) => {
        console.log(response.data)
          return response.data
      }).then((data) => {
        let areas = data.response.body.items.item;
        let sel = document.getElementById("search-area");
        areas.forEach((area) => {
          let opt = document.createElement("option");
          opt.setAttribute("value", area.code);
          opt.appendChild(document.createTextNode(area.name));

          sel.appendChild(opt);
        });
      })
  },
  unmounted() {},
  methods: {
    // api 불러오기
    loadScript() {
      const script = document.createElement("script");
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey="+import.meta.env.VITE_KAKAO_API_TOKEN+"&autoload=false"; 
      script.onload = () => window.kakao.maps.load(this.loadMap); 

      document.head.appendChild(script);
    },
    // 맵 출력하기
    loadMap() {
      const container = document.getElementById("map"); 
      const options = {
        center: new window.kakao.maps.LatLng(33.450701, 126.570667), 
        level: 3
      };

      this.map = new window.kakao.maps.Map(container, options); 
      this.loadMaker();
    },
    // 지정한 위치에 마커 불러오기
    loadMaker() {
      const markerPosition = new window.kakao.maps.LatLng(
        33.450701,
        126.570667
      );

      const marker = new window.kakao.maps.Marker({
        position: markerPosition,
      });

      marker.setMap(this.map);
    },

    

  },
};
</script>
