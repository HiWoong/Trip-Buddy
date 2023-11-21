<script setup>
const props = defineProps({
  myHotPlace: Object,
});
</script>

<template>
  <div class="myhotP">
    <div><img :src="props.myHotPlace.image" /></div>
    <div class="myhotContents">
      <div class="myhotTitle">
        [{{ props.myHotPlace.hotplaceId }}] {{ props.myHotPlace.subject }}
      </div>
      <div class="myhotContent">{{ props.myHotPlace.content }}</div>
      <div>{{ props.myHotPlace.createdDate }}</div>
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
.myhotP {
  display: flex;
  flex-direction: row;
  width: 1000px;
  height: 300px;
}
.myhotContents {
  display: flex;
  flex-direction: column;
  margin: 0 0 0 20px;
}
.myhotTitle {
  font-size: 30px;
}
.myhotContent {
  font-size: 20px;
}
</style>
