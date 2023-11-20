<script setup>
import { useRouter } from "vue-router";
const router = useRouter();
const props = defineProps({
  articleNo: Number,
  userId: String,
  subject: String,
  content: String,
  hit: Number,
  registerTime: String,
});

const moveArticle = () => {
  router.push({ name: "BoardDetail", params: { articleNo: props.articleNo } });
};
</script>
<template>
  <tr>
    <td class="articleNo">{{ articleNo }}</td>
    <td class="articleTitle">
      <div @click="moveArticle">{{ subject }}</div>
    </td>
    <td class="articleAuthor">{{ userId }}</td>
    <td class="articleHit">{{ hit }}</td>
    <td class="articleDate">{{ registerTime }}</td>
  </tr>
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
.articleNo {
  width: 100px;
  /* border: 1px solid #292929;
  border-radius: 5px; */
  margin: 0 50px 0 10px;
}
.articleTitle {
  width: 500px;
  /* border: 1px solid #292929;
  border-radius: 5px; */
  margin: 0 50px 0 50px;
  cursor: pointer;
}
.articleAuthor {
  width: 200px;
  /* border: 1px solid #292929;
  border-radius: 5px; */
  margin: 0 10px 0 10px;
}
.articleHit {
  width: 100px;
  /* border: 1px solid #292929;
  border-radius: 5px; */
  margin: 0 10px 0 10px;
}
.articleDate {
  width: 345px;
  /* border: 1px solid #292929;
  border-radius: 5px; */
}
</style>
