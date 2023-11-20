<script setup>
import http from "@/util/http-common.js";
import { ref, watch, onMounted } from "vue";
import { useRouter } from "vue-router";
import { info } from "@/api/userApi";
import { httpStatusCode } from "@/util/http-status";

import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();
const userId = cookies.get("userId");
const router = useRouter();
const data = ref({
  userId: userId,
  image: "../src/assets/img/noImage.png",
  subject: "",
  content: "",
});

const imageUpload = async (files) => {
  imageToBase64(files[0]);
};

function imageToBase64(f) {
  var reader = new FileReader();
  reader.readAsDataURL(f);
  reader.onload = function (e) {
    data.value.image = e.target.result;
  };
}

const hotPlaceWrite = () => {
  if (data.value.image != "../src/assets/img/noImage.png") {
    http.post("/hotplaceapi/write", data.value).then(({ data }) => {
      if (data == 1) {
        alert("등록이 완료되었습니다.");
        router.replace({ name: "AttractionHotPlaceList" });
      } else {
        alert("등록이 정상적으로 진행되지 않았습니다.");
      }
    });
  } else {
    alert("I need a picture...");
  }
};
</script>

<template>
  <div class="wholeLayout">
    <div id="image">
      <img v-bind:src="data.image" style="width: 100%; height: 100%; object-fit: cover" />
      <div style="justify-content: center; margin: 30px 0 0 275px">
        <label
          for="uploadImage"
          style="
            cursor: pointer;
            border: 1px solid gray;
            border-radius: 5px;
            padding: 5px;
            background-color: peachpuff;
            color: #292929;
            font-weight: bold;
            font-size: 25px;
          "
          >이미지 변경</label
        >
        <input
          multiple
          @change="imageUpload($event.target.files)"
          accept="image/*"
          type="file"
          id="uploadImage"
          style="display: flex; justify-content: center; visibility: hidden"
        />
      </div>
    </div>
    <div id="contents">
      <label for="hotTitle" style="width: 500px; text-align: start; font-size: 25px">제목</label>
      <div id="title">
        <input
          type="text"
          id="hotTitle"
          name="hotTitle"
          placeholder="장소에 어울리는 제목을 적어주세요!"
          style="width: 500px; height: 50px; padding-left: 15px"
          v-model="data.subject"
        />
      </div>
      <label for="hotContent" style="width: 500px; text-align: start; font-size: 25px"
        >부가내용</label
      >
      <div id="content">
        <textarea
          type="text"
          id="hotContent"
          name="hotContent"
          placeholder="장소에 어울리는 내용을 적어주세요!"
          style="width: 500px; height: 350px; padding: 10px 0 0 15px"
          rows="15"
          v-model="data.content"
        />
      </div>
      <div class="button" @click="hotPlaceWrite">
        <p class="btnText">등록하기</p>
      </div>
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
.wholeLayout {
  margin: 20px 0;
  width: 100%;
  height: 1000px;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  /* background-color: thistle; */
}
#image {
  width: 700px;
  height: 700px;
  margin-left: 150px;
  margin-bottom: 40px;
  /* background-color: antiquewhite; */
  border-radius: 70%;
}
#contents {
  margin: 100px 0 0 100px;
  width: 500px;
  height: 500px;
}
#title {
  margin: 0 0 100px 0;
}
#hotContent {
  width: 500px;
}
/* #button {
  
  width: 150px;
  height: 150px;
  background-color: antiquewhite;
} */
.button {
  margin: 0 0 0 18vh;
  background: #3d4c53;
  margin: 20px auto;
  width: 200px;
  height: 50px;
  overflow: hidden;
  text-align: center;
  /* transition: 0.1s; */
  cursor: pointer;
  border-radius: 3px;
  box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.2);
  font-size: 16px;
  color: white;
}
.btnText {
  margin: 14px 0 0 0;
  transition: 0.3s;
}
.button:hover {
  background-color: rgba(0, 0, 0, 0.3);
  color: #292929;
}
.button:active {
  /*Clicked and held*/
  box-shadow: 0px 5px 6px rgba(0, 0, 0, 0.3);
}
</style>
