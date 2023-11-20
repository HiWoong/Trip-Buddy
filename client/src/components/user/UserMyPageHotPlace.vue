<script setup>
const props = defineProps({
  favHotPlace: Object,
});
</script>

<template>
  <div class="favhotP">
    <div><img :src="props.favHotPlace.image" /></div>
    <div class="favhotContents">
      <div class="favhotTitle">
        [{{ props.favHotPlace.hotplaceId }}] {{ props.favHotPlace.subject }}
      </div>
      <div class="favhotContent">{{ props.favHotPlace.content }}</div>
      <div>{{ props.favHotPlace.createdDate }}</div>
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
.favhotP {
  display: flex;
  flex-direction: row;
  width: 1000px;
  height: 300px;
}
.favhotContents {
  display: flex;
  flex-direction: column;
  margin: 0 0 0 20px;
}
.favhotTitle {
  font-size: 30px;
}
.favhotContent {
  font-size: 20px;
}
</style>
