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
  <vueper-slides
    id="slideShow"
    width="500px"
    fixed-height="906px"
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
</template>

<style>
* {
  margin: 0;
  padding: 0;
}
#slideShow {
  margin-top: 57px;
}
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
  margin: 0 0 0 900px;
  font-size: 50px;
  color: yellowgreen;
}
.vueperslide__content {
  font-size: 20px;
  color: yellowgreen;
  margin: 0 0 800px 780px;
}
</style>
