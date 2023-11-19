<script setup>
import http from "@/util/http-common.js";
import { ref, watch, onMounted } from "vue";
import { useRouter } from "vue-router";
import { info } from "@/api/userApi";
import { httpStatusCode } from "@/util/http-status";

import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

const router = useRouter();
const data = ref({
  uploadImage: "",
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
    data.value.uploadImage = e.target.result;
  };
}
</script>

<template>
  <div class="wholeLayout">
    <div id="image">
      <img
        v-bind:src="data.uploadImage"
        style="width: 100%; height: 100%; object-fit: cover"
      />
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
</style>
