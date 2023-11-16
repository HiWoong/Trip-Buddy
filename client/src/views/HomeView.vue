<script setup>
import { VueperSlides, VueperSlide } from "vueperslides";
import { ref } from "vue";
import "vueperslides/dist/vueperslides.css";
const slides = ref([
  {
    title: "Temp Image1",
    content: "Temp Image1",
    image: "src/assets/img/default.jpg",
  },
  {
    title: "Temp Image2",
    content: "Temp Image2",
    image: "src/assets/img/default.jpg",
  },
  {
    title: "Temp Image3",
    content: "Temp Image1",
    image: "src/assets/img/default.jpg",
  },
]);
const test = (title) => {
  alert(title);
};
</script>

<template>
  <!-- fixed-height="863px" -->
  <vueper-slides
    id="slideShow"
    fixed-height="400px"
    fractions
    progress
    fade
    :touchable="false"
    :dragging-distance="70"
    prevent-y-scroll
    autoplay
  >
    <!-- @click="test(slide.title)" -->
    <vueper-slide
      v-for="(slide, i) in slides"
      :key="i"
      :image="slide.image"
      :title="slide.title"
      :content="slide.content"
    />
    <template #pause>
      <i class="icon pause_circle_outline"></i>
    </template>
  </vueper-slides>
  <div class="logo">Enjoy Trip</div>
  <div class="cards">
    <div class="card">
      <img src="../assets/img/default.jpg" alt="" />
      <div class="title">주인장 추천</div>
      <div class="content">한 번 잡숴봐</div>
    </div>
    <div class="card">
      <img src="../assets/img/default.jpg" alt="" />
      <div class="title">별점순</div>
      <div class="content">이게 진짜 좋은거라니까</div>
    </div>
    <div class="card">
      <img src="../assets/img/default.jpg" alt="" />
      <div class="title">좋아요순</div>
      <div class="content">내가 먹으려다 안먹고 양보하는거여</div>
    </div>
    <div class="card">
      <img src="../assets/img/default.jpg" alt="" />
      <div class="title">조회수순</div>
      <div class="content">으잉? 진짜 안먹을겨?</div>
    </div>
  </div>
</template>

<style>
* {
  margin: 0;
  padding: 0;
}
/* #slideShow {
  margin-top: 57px;
} */
.vueperslides__arrow {
  color: yellow;
}
.vueperslides__arrow svg {
  stroke-width: 2;
}
.vueperslides__progress {
  background: rgba(0, 0, 0, 0.25);
  color: rgb(255, 181, 23);
}
.vueperslide__title {
  margin: 0 0 0 1500px;
  font-size: 50px;
  color: yellowgreen;
}
.vueperslide__content {
  font-size: 20px;
  color: yellowgreen;
  margin: 0 0 770px 1350px;
}
.logo {
  width: 100%;
  height: 135px;
  text-align: center;
  display: block;
  font-size: 80px;
}
.cards {
  width: 90%;
  height: 500px;
  margin: 0 auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  /* background-color: antiquewhite; */
}
.card {
  background-color: rgb(222, 222, 222);
  display: inline-block;
  width: 350px;
  height: 400px;
  border-radius: 2vh;
  margin-bottom: 3vh;
  box-shadow: 2px 10px 10px 2px rgb(199, 199, 199);
}

img {
  width: 350px;
  height: 250px;
}
.title {
  text-align: center;
  font-size: 30px;
}
.content {
  margin: auto 0;
  text-align: center;
  font-size: 20px;
}
</style>
