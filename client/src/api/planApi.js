import http from "@/util/http-common.js";

import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

async function totalPlans(userId, success, fail) {
  await http.get(`/planapi/view/${userId}`).then(success).catch(fail);
}

async function deletePlanByPlanId(planId, success, fail) {
  await http.get(`/planapi/delete/${planId}`).then(success).catch(fail);
}

export { totalPlans, deletePlanByPlanId };
