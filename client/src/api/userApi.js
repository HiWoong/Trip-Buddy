// import { localAxios } from "@/util/http-commons";
import http from "@/util/http-common.js";

import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();
// const local = localAxios();

async function join(user, success, fail) {
  console.log("param", param);
  await http.post(`/userapi/join`, user).then(success).catch(fail);
  console.log("userConfirm ok");
}

async function login(user, success, fail) {
  http.defaults.headers["Authorization"] = cookies.get("accessToken");
  // http.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await http.post(`/userapi/login`, user).then(success).catch(fail);
}

async function logout(userId, success, fail) {
  await http.get(`/userapi/logout/${userId}`).then(success).catch(fail);
}

async function update(user, success, fail) {
  http.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await http.post(`/userapi/update`, user).then(success).catch(fail);
}

async function withdraw(userId, success, fail) {
  http.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await http.get(`/userapi/delete/${userId}`).then(success).catch(fail);
}

async function info(userId, success, fail) {
  http.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await http.get(`/userapi/info/${userId}`).then(success).catch(fail);
}

async function tokenRegeneration(userId, success, fail) {
  http.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await http.post(`/userapi/refresh`, userId).then(success).catch(fail);
}

export { join, login, logout, update, withdraw, info, tokenRegeneration };
