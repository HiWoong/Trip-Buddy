<script setup>
import { VueperSlides, VueperSlide } from "vueperslides";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import "vueperslides/dist/vueperslides.css";

import { useAttractionStore } from "@/stores/attractionStore";
import { useMenuStore } from "@/stores/menuStore.js";
import { storeToRefs } from "pinia";

import { httpStatusCode } from "@/util/http-status";
import { getOne } from "@/api/hotplaceApi.js";

const attractionStore = useAttractionStore();
const { setSidoCode, setTrueClickHome } = attractionStore;

const menuStore = useMenuStore();
const { menuFlag, flagName } = storeToRefs(menuStore);

const router = useRouter();

const sortNew = ref("");
const sortLike = ref("");
const sortVisit = ref("");

onMounted(() => {
  if (sessionStorage.getItem("isLogin") != null) {
    sessionStorage.removeItem("isLogin");
    router.go();
  }
  sortImage("created_date", sortNew);
  sortImage("hit_count", sortLike);
  sortImage("visited_count", sortVisit);
});

const sortImage = async (sortType, target) => {
  getOne(
    sortType,
    (response) => {
      if (response.status == httpStatusCode.OK) {
        target.value = response.data.image;
      }
    },
    (error) => {
      console.error(error);
    }
  );
};

const slides = ref([
  {
    title: "Seoul",
    content: "Seoul Seoul",
    image: "src/assets/img/Seoul.jpg",
    sido: 1,
  },
  {
    title: "Incheon",
    content: "Incheon Incheon",
    image: "src/assets/img/Incheon.jpg",
    sido: 2,
  },
  {
    title: "Busan",
    content: "Busan Busan",
    image: "src/assets/img/Busan.jpg",
    sido: 6,
  },
  {
    title: "Daejeon",
    content: "Daejeon Daejeon",
    image: "src/assets/img/Daejeon.jpg",
    sido: 3,
  },
  {
    title: "Daegu",
    content: "Daegu Daegu",
    image: "src/assets/img/Daegu.jpg",
    sido: 4,
  },
  {
    title: "Gwangju",
    content: "Gwangju Gwangju",
    image: "src/assets/img/Gwangju.jpg",
    sido: 5,
  },
]);

const goAttraction = async (num) => {
  setSidoCode(num);
  setTrueClickHome();
  router.replace({ name: "AttractionSearch" });
};

const moveHotplaceSequence = async (num) => {
  if (num === 1) {
    menuFlag.value = "created_date";
    flagName.value = "최신순";
  } else if (num === 2) {
    menuFlag.value = "visited_count";
    flagName.value = "조회순";
  } else {
    menuFlag.value = "hit_count";
    flagName.value = "좋아요순";
  }
  router.push({ name: "AttractionHotPlaceList" });
};
</script>

<template>
  <vueper-slides
    fixed-height="430px"
    autoplay
    arrows-outside
    transition-speed="250"
    fade
    :touchable="false"
  >
    <vueper-slide
      v-for="(slide, i) in slides"
      :key="i"
      :image="slide.image"
      :title="slide.title"
      :content="slide.content"
      @click="goAttraction(slide.sido)"
      class="mainPhoto"
    />
  </vueper-slides>
  <div class="cards">
    <div class="card" @click="moveHotplaceSequence(1)">
      <img :src="sortNew" alt="#" style="border-radius: 5px; object-fit: cover" />
      <div class="title">최신순</div>
      <div class="content">최신순으로 정렬된 핫 플레이스</div>
    </div>
    <div class="card" @click="moveHotplaceSequence(2)">
      <img :src="sortVisit" alt="#" style="border-radius: 5px; object-fit: cover" />
      <div class="title">조회순</div>
      <div class="content">조회순으로 정렬된 핫 플레이스</div>
    </div>
    <div class="card" @click="moveHotplaceSequence(3)">
      <img :src="sortLike" alt="#" style="border-radius: 5px; object-fit: cover" />
      <div class="title">좋아요순</div>
      <div class="content">좋아요순으로 정렬된 핫 플레이스</div>
    </div>
  </div>
</template>

<style>
* {
  margin: 0;
  padding: 0;
}
.mainPhoto {
  cursor: pointer;
}
.vueperslides__arrow {
  color: black;
}
.vueperslides__arrow svg {
  stroke-width: 2;
}
.vueperslides__progress {
  background: rgba(0, 0, 0, 0.25);
  color: rgb(255, 181, 23);
}
.vueperslide__title {
  font-size: 80px;
  font-weight: bold;
  color: whitesmoke;
}
.vueperslide__content {
  font-size: 50px;
  color: whitesmoke;
}
.vueperslides__inner {
  width: 1520px;
  margin: 10px auto;
}
.cards {
  width: 80%;
  height: 400px;
  margin: 40px auto 20px auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}
.card {
  background-color: rgb(222, 222, 222);
  display: inline-block;
  width: 299px;
  height: 400px;
  border-radius: 2vh;
  margin-bottom: 3vh;
  box-shadow: 2px 10px 10px 2px rgb(199, 199, 199);
  cursor: pointer;
}

img {
  width: 297px;
  height: 250px;
}
.title {
  text-align: center;
  font-size: 25px;
}
.content {
  margin: 25px 0 0 0;
  text-align: center;
  font-size: 15px;
}
</style>
