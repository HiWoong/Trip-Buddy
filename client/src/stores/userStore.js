import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import {
  join,
  login,
  logout,
  update,
  withdraw,
  info,
  tokenRegeneration,
  getFavorites,
  getMyFavoriteHotPlace,
} from "@/api/userApi";
import { httpStatusCode } from "@/util/http-status";

// cookies
import { useCookies } from "vue3-cookies";
import { setFavorites } from "../api/userApi";
const { cookies } = useCookies();

export const useUserStore = defineStore("userStore", () => {
  const router = useRouter();

  const isLogin = ref(false);
  const isLoginError = ref(false);
  const userInfo = ref(null);
  const isValidToken = ref(false);
  const favorites = ref([]);
  const myFavHotPlaces = ref([]);

  const setmyFavHotPlaces = () => {
    myFavHotPlaces.value = [];
  };
  const getmyFavHotPlaces = () => {
    return myFavHotPlaces.value;
  };

  const getFavHotPlace = async (hotPlaceId) => {
    await getMyFavoriteHotPlace(
      hotPlaceId,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          let { data } = response;
          myFavHotPlaces.value.push(data);
          // myFavHotPlaces.value.push(data);
          // console.log(myFavHotPlaces);
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const getLikes = () => {
    return favorites.value;
  };

  const setFavorite = async (data) => {
    await setFavorites(
      data,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          alert("좋아요가 성공적으로 완료되었습니다.");
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const getFavorite = async (userId) => {
    await getFavorites(
      userId,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          // console.log(response);
          let { data } = response;
          favorites.value = data;
          // let arr = JSON.parse(data);
          // console.log(arr);
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const userLoginStore = async (loginUser) => {
    await login(
      loginUser,
      (response) => {
        console.log("login ok!!!!", response.status);
        console.log("login ok!!!!", httpStatusCode.CREATE);
        if (response.status === httpStatusCode.CREATE) {
          let { data } = response;
          // console.log("data", data);
          let accessToken = data["accessToken"];
          let refreshToken = data["refreshToken"];
          console.log("accessToken", accessToken);
          console.log("refreshToken", refreshToken);
          isLogin.value = true;
          isLoginError.value = false;
          isValidToken.value = true;
          console.log(userInfo.value);
          cookies.set("accessToken", accessToken, 3600);
          cookies.set("refreshToken", refreshToken);
          makeUserIdCookieStore(cookies.get("accessToken"));
          router.push("/");
        } else {
          console.log("로그인 실패했다");
          isLogin.value = false;
          isLoginError.value = true;
          isValidToken.value = false;
        }
      },
      (error) => {
        console.log("아이디와 비밀번호를 다시 확인하도록");
        alert("아이디와 비밀번호를 확인하도록");
        console.error(error);
      }
    );
  };

  const userLogoutStore = async (userId) => {
    await logout(
      userId,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          isLogin.value = false;
          cookies.remove("userId");
          cookies.remove("accessToken");
          cookies.remove("refreshToken");
        } else {
          alert.error("유저 정보가 없습니다.");
        }
      },
      (error) => {
        alert("아이디와 비밀번호를 확인해주세요 !");
        console.log(error);
      }
    );
  };

  const userDeleteStore = async (userId) => {
    // console.log("userStore.userDeleteStore => userId : ", userId);
    await withdraw(
      userId,
      (response) => {
        if (response.status === httpStatusCode.NOCONTENT) {
          console.log("userStore.userDeleteStore => status : ", response.status);
          isLogin.value = false;
          cookies.remove("userId");
          cookies.remove("accessToken");
          cookies.remove("refreshToken");
        } else {
          console.error("유저 정보 없음 !!!");
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const makeUserIdCookieStore = (token) => {
    let decodeToken = jwtDecode(token);
    console.log(decodeToken.userId);
    cookies.set("userId", decodeToken.userId, 3600);
  };

  const userJoinStore = async (joinUser) => {
    join(
      joinUser,
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          alert("사용자 정보 등록이 완료되었습니다.");
          router.push("/");
        } else {
          console.log("Error status : ", response.status);
        }
      },
      (error) => {
        alert("중복된 아이디입니다!");
      }
    );
  };

  return {
    isLogin,
    isLoginError,
    userInfo,
    isValidToken,
    userLoginStore,
    userLogoutStore,
    userDeleteStore,
    userJoinStore,
    makeUserIdCookieStore,
    setFavorite,
    getFavorite,
    getLikes,
    getFavHotPlace,
    getmyFavHotPlaces,
    setmyFavHotPlaces,
  };
});
