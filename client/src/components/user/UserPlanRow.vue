<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { RouterView } from "vue-router";
import http from "@/util/http-common.js";

import { deletePlanByPlanId } from "@/api/planApi";
import { httpStatusCode } from "@/util/http-status";

const router = useRouter();

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

    // console.log("plans[i][j] : ", plans[i][j]);
  }
}
// console.log("palns . after : ", plans);

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

const isModalOpen = ref(false);

const changeModalState = () => {
  isModalOpen.value = isModalOpen.value == false ? true : false;
};
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
    <p>{{ props.createDate }}</p>
    <p>{{ props.subject }}</p>
    <!-- <button type="button" class="btn btn-primary" @click="changeModalState">
      보기
    </button> -->

    <button
      type="button"
      class="btn btn-primary"
      data-bs-toggle="modal"
      :data-bs-target="'#modal' + props.planId"
    >
      상세보기
    </button>

    <!-- Modal -->
    <div
      class="modal fade"
      :id="'modal' + props.planId"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <!-- <div
      class="modal fade"
      :id="'modal' + props.planId"
    > -->
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content" style="z-index: 9999">
          <div class="modal-header">
            <h5 class="modal-title" :id="'modal' + props.planId">
              {{ props.createDate }}
            </h5>
          </div>
          <div class="modal-body">
            <template v-for="(plan, index) in plans">
              <div>
                <template v-if="plan[0] != null">
                  <div>~~ {{ index + 1 }} 일차 ~~</div>
                  <ol class="numbered">
                    <template v-for="p in plan">
                      <template v-if="p != null">
                        <li>
                          {{ p.place_name }}, {{ p.road_address_name }}, {{ p.phone }}
                        </li>
                      </template>
                    </template>
                  </ol>
                </template>
              </div>
            </template>
          </div>
          <div class="modal-footer">
            <!-- <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Close
            </button> -->
            <button type="button" class="btn btn-primary" @click="deletePlan">
              삭제
            </button>
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              닫기
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- <div v-show="isModalOpen">
      <template v-for="(plan, index) in plans">
        <div>
          <template v-if="plan[0] != null">
            <div>~~ {{ index + 1 }} 일차 ~~</div>
            <template v-for="p in plan">
              <template v-if="p != null">
                <p>
                  {{ p.place_name }}, {{ p.road_address_name }},
                  {{ p.address_name }}, {{ p.phone }}
                </p>
              </template>
            </template>
          </template>
        </div>
      </template>
      <button type="button" class="btn btn-primary" @click="deletePlan">
        삭제
      </button>
    </div> -->
  </div>
</template>
<style scoped>
/* .modal-backdrop {
  z-index: -1;
  background-color: transparent;
}

ol.numbered {
  counter-reset: numbered-list;
  margin-left: 10px;
  position: relative;
}

li {
font-size: 16px;
line-height: 1.2;
margin-bottom: 30px;
padding-left: 30px;
} */
</style>
