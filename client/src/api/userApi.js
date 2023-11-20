// import { localAxios } from "@/util/http-commons";
import http from "@/util/http-common.js";

import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();
// const local = localAxios();

async function join(user, success, fail) {
  await http.post(`/userapi/join`, user).then(success).catch(fail);
}

async function login(user, success, fail) {
  http.defaults.headers["Authorization"] = cookies.get("accessToken");
  await http.post(`/userapi/login`, user).then(success).catch(fail);
}

async function logout(userId, success, fail) {
  await http.post(`/userapi/logout`, userId).then(success).catch(fail);
}

async function update(user, success, fail) {
  http.defaults.headers["Authorization"] = cookies.get("accessToken");
  await http.post(`/userapi/update`, user).then(success).catch(fail);
}

async function withdraw(userId, success, fail) {
  http.defaults.headers["Authorization"] = cookies.get("accessToken");
  await http.get(`/userapi/delete/${userId}`).then(success).catch(fail);
}

async function info(userId, success, fail) {
  http.defaults.headers["Authorization"] = cookies.get("accessToken");
  await http.get(`/userapi/info/${userId}`).then(success).catch(fail);
}

async function tokenRegeneration(userId, success, fail) {
  http.defaults.headers["refreshToken"] = cookies.get("refreshToken"); //axios header에 refresh-token 셋팅
  await http.post(`/userapi/refresh`, userId).then(success).catch(fail);
}

async function getFavorites(userId, success, fail) {
  http.defaults.headers["Authorization"] = cookies.get("accessToken");
  await http.get(`/userapi/favorite/${userId}`).then(success).catch(fail);
}
async function setFavorites(data, success, fail) {
  http.defaults.headers["Authorization"] = cookies.get("accessToken");
  await http.post(`/userapi/favorite`, data).then(success).catch(fail);
}

async function getMyFavoriteHotPlace(hotPlaceId, success, fail) {
  http.defaults.headers["Authorization"] = cookies.get("accessToken");
  await http.get(`/hotplaceapi/readFavorite/${hotPlaceId}`).then(success).catch(fail);
}

export {
  join,
  login,
  logout,
  update,
  withdraw,
  info,
  tokenRegeneration,
  getFavorites,
  setFavorites,
  getMyFavoriteHotPlace,
};
