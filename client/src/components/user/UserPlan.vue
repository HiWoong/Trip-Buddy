<script setup>
import http from "@/util/http-common.js";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { RouterView } from "vue-router";

import UserPlanRow from "@/components/user/UserPlanRow.vue";

import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/userStore.js";

import { info } from "@/api/userApi";
import { totalPlans } from "@/api/planApi";
import { httpStatusCode } from "@/util/http-status";

// cookies
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

const userStore = useUserStore();
const { userDeleteStore } = userStore;

// import { useUserStore} from "@/stores/userStore.js";
// const userStore = useUserStore();

// const { userInfo } = storeToRefs(userStore);
// const { getUserInfo } = userStore;

const router = useRouter();

const newUser = ref([]);
const plans = ref([]);

// temp
const profileImg = ref(
  "https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
);
//

onMounted(async () => {
  console.log("gogo getUserInfo : ", cookies.get("userId"));
  getUserInfo(cookies.get("userId"));
  getTotalPlans(cookies.get("userId"));
});

const getUserInfo = async (userId) => {
  info(
    userId,
    (response) => {
      console.log(response.status);
      if (response.status === httpStatusCode.OK) {
        console.log("3. getUserInfo data >> ", response.data.userInfo);
        newUser.value = response.data.userInfo;
        
      } else {
        console.log("유저 정보 없음!!!!");
      }
    },
    async (error) => {
      console.error(
        "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
        error.response.status
      );
    }
  );
};

const getTotalPlans = async (userId) => {
  totalPlans(
    userId,
    (response) => {
      console.log(response.status);
      if (response.status === httpStatusCode.OK) {
        console.log("plans >> ", response.data);
        plans.value = response.data;

        // console.log("new User", newUser.value);
        // console.log("new User ProfileImg : ", newUser.value.profileImage);
      } else {
        console.log("유저 정보 없음!!!!");
      }
    },
    async (error) => {
      console.error(
        "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
        error.response.status
      );
    }
  );
}
</script>

<template>
  <div class="container mt-5 pt-5">
    <div class="row justify-content-center mt-5">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">마이페이지</mark>
        </h2>
        <div>
          <UserPlanRow v-for="plan in plans" :key="plan.planId" v-bind="plan" />
        </div>
      </div>
      <div class="mb-3"></div>
    </div>
  </div>
</template>

<style>
.modal-backdrop {
  z-index: 0;
  background-color: transparent;
}

.modal-content {
  z-index: 10;
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
}

</style>
