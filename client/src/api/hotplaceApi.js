import http from "@/util/http-common.js";

import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

async function updateVisitedCount(hotPlaceDto, success, fail) {
  http.defaults.headers["Authorization"] = cookies.get("accessToken");
  await http
    .post(`/hotplaceapi/updateVisited`, hotPlaceDto)
    .then(success)
    .catch(fail);
}

async function addHitCount(hotPlaceDto, success, fail) {
  http.defaults.headers["Authorization"] = cookies.get("accessToken");
  await http
    .post(`/hotplaceapi/addHitCount`, hotPlaceDto)
    .then(success)
    .catch(fail);
}

async function minHitCount(hotPlaceDto, success, fail) {
  http.defaults.headers["Authorization"] = cookies.get("accessToken");
  await http
    .post(`/hotplaceapi/minHitCount`, hotPlaceDto)
    .then(success)
    .catch(fail);
}

async function deleteHotplace(hotplaceId, success, fail) {
  http.defaults.headers["Authorization"] = cookies.get("accessToken");
  await http
    .get(`/hotplaceapi/delete/${hotplaceId}`)
    .then(success)
    .catch(fail);
}

async function updateHotplace(hotplaceId, success, fail) {
    http.defaults.headers["Authorization"] = cookies.get("accessToken");
    await http
      .post(`/hotplaceapi/update`)
      .then(success)
      .catch(fail);
  }

export { updateVisitedCount, addHitCount, minHitCount, deleteHotplace, updateHotplace };
