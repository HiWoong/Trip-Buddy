<script setup>
import { ref } from "vue";
import { deleteHotplace, updateHotplace } from "@/api/hotplaceApi";
import { useRouter } from "vue-router";
import { httpStatusCode } from "@/util/http-status";

const router = useRouter();
const isModify = ref(false);

const props = defineProps({
  myHotPlace: Object,
});

const emit = defineEmits(["changeMyFav"]);

const newPost = ref({
  hotplaceId: props.myHotPlace.hotplaceId,
  subject: props.myHotPlace.subject,
  content: props.myHotPlace.content,
});

const deleteMyHotplace = async () => {
  deleteHotplace(
    props.myHotPlace.hotplaceId,
    (response) => {
      if (response.status === httpStatusCode.NOCONTENT) {
        alert("삭제가 완료되었습니다.");
        emit("changeMyFav");
        // router.go();
      } else {
        console.log("Unexcepted Error");
      }
    },
    (error) => {
      console.error(error);
    }
  );
};

const changeModify = () => {
  isModify.value = !isModify.value;
};

const modifyMyHotPlace = () => {
  updateHotplace(
    newPost.value,
    (response) => {
      if (response.status === httpStatusCode.OK) {
        alert("수정이 완료되었습니다.");
        changeModify();
        emit("changeMyFav");
      } else {
        console.log("Unexcepted Error");
      }
    },
    (error) => {
      console.error(error);
    }
  );
};
</script>

<template>
  <div class="myhotP">
    <div><img :src="props.myHotPlace.image" class="myhotImage" /></div>
    <div class="myhotContents">
      <div class="myhotTitle">
        [{{ props.myHotPlace.hotplaceId }}] {{ props.myHotPlace.subject }}
      </div>
      <div class="myhotContent">{{ props.myHotPlace.content }}</div>
      <div>{{ props.myHotPlace.createdDate }}</div>
      <div class="buttons">
        <div class="modifyButtonDiv">
          <input @click="changeModify" class="modifyButton" type="button" value="수정" />
        </div>
        <div class="deleteButtonDiv">
          <input @click="deleteMyHotplace" class="deleteButton" type="button" value="삭제" />
        </div>
      </div>
    </div>
  </div>
  <div v-if="isModify">
    <div class="modifyWindow">
      <div>
        <div class="modifyWindowSubject">
          <div style="font-size: 18px; margin-right: 10px">제목</div>
          <input
            type="text"
            v-model="newPost.subject"
            style="padding-left: 5px; border: 2px solid gray; border-radius: 5px"
          />
        </div>
        <div class="modifyWindowContent">
          <div style="font-size: 18px; margin-right: 10px">내용</div>
          <textarea
            class="modifyPlaceContent"
            type="text"
            v-model="newPost.content"
            style="padding-left: 5px; border: 2px solid gray; border-radius: 5px"
          />
        </div>
      </div>
      <div class="modifyWindowButtonDiv">
        <input class="modifyWindowButton" type="button" value="수정" @click="modifyMyHotPlace" />
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
.myhotP {
  display: flex;
  flex-direction: row;
  width: 1000px;
  height: 250px;
  border: 3px dashed gray;
  border-radius: 15px;
  padding: 5px;
  margin: 5px 0;
  word-break: break-all;
}
.myhotImage {
  width: 250px;
  height: 230px;
}
.myhotContents {
  display: flex;
  flex-direction: column;
  margin: 20px 0 0 20px;
}
.myhotTitle {
  margin: 10px 0 5px 0;
  font-size: 30px;
}
.myhotContent {
  font-size: 20px;
  height: 60px;
}
.buttons {
  display: flex;
  flex-direction: row;
}
.deleteButtonDiv {
  width: 50px;
  height: 30px;
  margin: 2vh 0 0 5vh;
}
.deleteButton {
  width: 70px;
  height: 35px;
  background-color: rgb(247, 127, 157);
  border-radius: 5px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px rgb(247, 127, 157);
  font-size: 16px;
  font-weight: 600;
}
.deleteButton:hover {
  box-shadow: 0px 0px 0px 0px rgb(247, 127, 157);
  margin-top: 5px;
  margin-bottom: 5px;
}
.modifyButtonDiv {
  width: 50px;
  height: 30px;
  margin: 2vh 0 0 528px;
}
.modifyButton {
  width: 70px;
  height: 35px;
  background-color: rgb(178, 214, 111);
  border-radius: 5px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px rgb(178, 214, 111);
  font-size: 16px;
  font-weight: 600;
}
.modifyButton:hover {
  box-shadow: 0px 0px 0px 0px rgb(178, 214, 111);
  margin-top: 5px;
  margin-bottom: 5px;
}
.modifyWindow {
  border: 3px dashed rgb(134, 196, 247);
  border-radius: 15px;
  margin: 5px 0 5px 0;
  padding: 10px;
  width: 700px;
  height: 200px;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
.modifyPlaceContent {
  resize: none;
  width: 500px;
  height: 100px;
}
.modifyWindowSubject {
  display: flex;
  flex-direction: row;
  font-size: 16px;
  margin: 0 0 10px 0;
}
.modifyWindowContent {
  display: flex;
  flex-direction: row;
  font-size: 16px;
  margin: 0 0 10px 0;
}
.modifyWindowButtonDiv {
  width: 70px;
  height: 100%;
  display: flex;
  align-items: center;
}
.modifyWindowButton {
  width: 70px;
  height: 40px;
  color: darkgray;
  border: 3px solid rgb(255, 191, 107);
  border-radius: 10px;
  font-size: 18px;
  box-sizing: border-box;
  box-shadow: 0 1px 2px rgb(255, 191, 107);
  transition: 0.25s;
  font-weight: 800;
}
.modifyWindowButton:hover {
  background-color: rgb(255, 191, 107);
  color: #593f61;
}
</style>
