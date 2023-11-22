<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { RouterView } from "vue-router";
import http from "@/util/http-common.js";

import { deletePlanByPlanId } from "@/api/planApi";
import { httpStatusCode } from "@/util/http-status";
import { useAttractionStore } from "@/stores/attractionStore.js";
const attractionStore = useAttractionStore();
const { getIsModalOpen, setIsModalOpen } = attractionStore;

const router = useRouter();
const isModalOpen = ref(false);
onMounted(() => {
  isModalOpen.value = getIsModalOpen();
});

const props = defineProps({
  planId: Number,
  userId: String,
  subject: String,
  content1: String,
  content2: String,
  content3: String,
  content4: String,
  content5: String,
  createDate: String,
  updateDate: String,
});

let plans = [];
plans.push(props.content1.slice(1, -1).split("},"));
plans.push(props.content2.slice(1, -1).split("},"));
plans.push(props.content3.slice(1, -1).split("},"));
plans.push(props.content4.slice(1, -1).split("},"));
plans.push(props.content5.slice(1, -1).split("},"));
// console.log("palns , before : ", plans);

for (let i = 0; i < plans.length; i++) {
  for (let j = 0; j < plans[i].length; j++) {
    if (j < plans[i].length - 1) {
      plans[i][j] = plans[i][j] + "}";
    }
    plans[i][j] = "" + plans[i][j];
    if (plans[i][j] == "") {
      plans[i][j] = null;
    } else {
      plans[i][j] = JSON.parse(plans[i][j]);
    }
  }
}

const deletePlan = () => {
  deletePlanByPlanId(
    props.planId,
    (response) => {
      if (response.status === httpStatusCode.NOCONTENT) {
        router.go();
      } else {
        console.log("계획 정보 없음!!!!");
      }
    },
    async (error) => {
      console.error("Unexcepted Error : ", error.response.status);
    }
  );
};

const changeModalState = async () => {
  await setIsModalOpen();
  isModalOpen.value = getIsModalOpen();
};
</script>
<template>
  <div class="wholePlanContents">
    <div class="firstContent">
      <div>
        <div style="font-size: 20px">{{ props.createDate }}</div>
        <div style="font-size: 30px; font-family: 'NanumSquareB'">{{ props.subject }}</div>
      </div>
      <div>
        <button class="confirmDetail" type="button" @click="changeModalState">상세보기</button>
      </div>
    </div>
  </div>

  <div class="wrap" v-if="isModalOpen">
    <div class="DetailPlancontents">
      <div v-for="(plan, index) in plans">
        <div class="planText">
          <template v-if="plan[0] != null">
            <div class="days">{{ index + 1 }} 일차</div>
            <div class="arrivals">
              <template v-for="p in plan">
                <template v-if="p != null">
                  <div>도착지: {{ p.place_name }}, {{ p.road_address_name }}, {{ p.phone }}</div>
                </template>
              </template>
            </div>
          </template>
        </div>
      </div>
      <div class="buttons">
        <input class="deleteButton" type="button" @click="deletePlan" value="삭제" />
        <input class="closeButton" type="button" @click="changeModalState" value="닫기" />
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
.wholePlanContents {
  position: relative;
  margin: 20px 0;
  display: flex;
  width: 600px;
  height: 100px;
  word-break: break-all;
  background-color: transparent;
  flex-direction: column;
  border: 3px dashed gray;
  border-radius: 15px;
}
.wrap {
  margin: 0 0 0 -659px;
  top: 0;
  position: fixed;
  background: rgba(0, 0, 0, 0.4);
  z-index: 1;
  width: 100%;
  height: 1000px;
}
.DetailPlancontents {
  border: 2px solid gray;
  border-radius: 15px;
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 700px;
  background: #fff;
  padding: 15px;
  box-sizing: border-box;
}
.planText {
  margin-left: 2px;
  font-size: 18px;
}
.firstContent {
  height: 100px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px 0 20px;
}
.confirmDetail {
  width: 70px;
  height: 80px;
}
.confirmDetail {
  background-color: #86b7fe;
  border-radius: 5px;
  transition: all 0.25s;
  box-shadow: 1px 4px 0px 0px #86b7fe;
  font-size: 16px;
  font-weight: 600;
}
.confirmDetail:hover {
  box-shadow: 0px 0px 0px 0px #86b7fe;
  margin-top: 5px;
  margin-bottom: 3px;
}
.days {
  width: 75px;
  font-size: 22px;
  border: 2px dashed skyblue;
  border-radius: 10px;
  text-align: center;
}
.arrivals {
  margin: 5px 0;
}
.buttons {
  display: flex;
  justify-content: space-between;
}
.deleteButton {
  border: 3px solid rgb(247, 127, 157);
  color: darkgray;
  box-sizing: border-box;
  padding: 5px 15px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 800;
  transition: 0.25s;
}
.deleteButton:hover {
  background-color: rgb(247, 127, 157);
  color: #d7fff1;
}
.closeButton {
  border: 3px solid rgb(125, 189, 88);
  color: darkgray;
  box-sizing: border-box;
  padding: 5px 15px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 800;
  transition: 0.25s;
}
.closeButton:hover {
  background-color: rgb(125, 189, 88);
  color: #d7fff1;
}
</style>
