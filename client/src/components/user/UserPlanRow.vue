<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { RouterView } from "vue-router";
import http from "@/util/http-common.js";

import { deletePlanByPlanId } from "@/api/planApi";
import { httpStatusCode } from "@/util/http-status";

const router = useRouter();

const props = defineProps({
  planId : Number,
  userId : String,
  subject : String,
  content1 : String,
  content2 : String,
  content3 : String,
  content4 : String,
  content5 : String,
  createDate : String,
  updateDate : String,
});



// const jcontent1 = ref(props.content1).value.slice(0, -1);
// console.log("substring : ", jcontent1.value.slice(0, -1));
// jcontent1 = props.content1.slice(0, -1);

// content1.value = content1.value
// const temp = props.content1.slice(1, -1);
// const arr = props.content1.slice(1, -1).split("},");
// for (let i = 0; i < arr.length; i++){
//     console.log("arr lenght : ", arr.length);
//     if (i < arr.length-1){
//         arr[i] = arr[i] + "}";
//     }
//     arr[i] = JSON.parse(arr[i]);
//     console.log("arr []", i, " : ", arr[i]);
// }

let plans = [];
// plans.value.push(props.content1.slice(1, -1).split("},"));
// plans.value.push(props.content2.slice(1, -1).split("},"));
// plans.value.push(props.content3.slice(1, -1).split("},"));
// plans.value.push(props.content4.slice(1, -1).split("},"));
// plans.value.push(props.content5.slice(1, -1).split("},"));
plans.push(props.content1.slice(1, -1).split("},"));
plans.push(props.content2.slice(1, -1).split("},"));
plans.push(props.content3.slice(1, -1).split("},"));
plans.push(props.content4.slice(1, -1).split("},"));
plans.push(props.content5.slice(1, -1).split("},"));
// console.log("palns , before : ", plans);

for (let i = 0; i < plans.length; i++){
    for (let j = 0; j < plans[i].length; j++){
        if (j < plans[i].length - 1 ){
            plans[i][j] = plans[i][j] + "}";
        }
        plans[i][j] = "" + plans[i][j];
        if (plans[i][j] == ""){
            plans[i][j] = null;
        } else {
            plans[i][j] = JSON.parse(plans[i][j]);
        }
        // plans[i][j] = plans[i][j].toString();

        // console.log("plans : ", i, j, " >> ", plans[i][j].toString());
        // console.log("plans[i][j] : ", plans[i][j]);
    }
}
// console.log("palns . after : ", plans);

// console.log("temp : ", temp);
// console.log("arr[0] : ", arr[0]);

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
      console.error(
        "Unexcepted Error : ",
        error.response.status
      );
    }
  );
}
</script>
<template>
  <!-- <tr class="text-center">
    <th scope="row">{{ articleNo }}</th>
    <td class="text-start">
      <RouterLink
        class="nav-link active"
        aria-current="page"
        :to="{ name: 'BoardDetail', params: { articleNo } }"
      >
        <a class="article-title link-dark" data-no="${{ articleNo }}" style="text-decoration: none">
          {{ subject }}
        </a>
      </RouterLink>
    </td>
    <td>{{ userId }}</td>
    <td>{{ hit }}</td>
    <td>{{ registerTime }}</td>
  </tr> -->
  <div>
    <!-- <p>{{ planId }}, {{ userId }}, {{ subject }}</p> -->
    <p>{{ props.createDate }}</p>
    <p>{{ props.subject }}</p>
    <div v-for="(plan, index) in plans">
        <div v-if="plan[0] != null">    
            <div> ~~ {{ index+1 }} 일차 ~~ </div>
            <div v-for="p in plan">
                <div v-if="p != null">
                    <p>{{ p.place_name }}, {{ p.road_address_name }}, {{ p.address_name }}, {{ p.phone }}</p>
                </div>
            </div>
        </div>
    </div>
    <button type="button" class="btn btn-primary" @click="deletePlan">삭제</button>
    <!-- <p>{{ plans[0][0]}}</p> -->
    <!-- <p>{{ jcontent1 }}</p> -->
    <!-- <p> {{ content2 }}</p> -->
  </div>
</template>
<style scoped></style>
