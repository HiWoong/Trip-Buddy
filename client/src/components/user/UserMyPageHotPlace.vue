<script setup>
const props = defineProps({
  favHotPlace: Object,
});
</script>

<template>
  <div class="wholeLayout">
    <div><img :src="props.favHotPlace.image" /></div>
    <div>{{ props.favHotPlace.hotplaceId }} {{ props.favHotPlace.subject }}</div>
    <div>{{ props.favHotPlace.content }}</div>
    <div>{{ props.favHotPlace.createdDate }}</div>
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
.wholeLayout {
  width: 1000px;
}
</style>
