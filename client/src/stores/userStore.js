import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import { join, login, logout, withdraw, getFavorites, getMyFavoriteHotPlace } from "@/api/userApi";
import { httpStatusCode } from "@/util/http-status";

import { useCookies } from "vue3-cookies";
import { getMyHotPlace, setFavorites } from "../api/userApi";
const { cookies } = useCookies();

export const useUserStore = defineStore("userStore", () => {
  const router = useRouter();

  const isLogin = ref(false);
  const isLoginError = ref(false);
  const userInfo = ref(null);
  const isValidToken = ref(false);
  const favorites = ref([]);
  const myFavHotPlaces = ref([]);
  const myStorageHotPlaces = ref([]);

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
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const setmyStorageHotPlace = async (userId) => {
    myStorageHotPlaces.value = [];

    await getMyHotPlace(
      userId,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          myStorageHotPlaces.value = response.data;
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const getmyStorageHotPlace = async () => {
    return myStorageHotPlaces.value;
  };

  const getLikes = () => {
    return favorites.value;
  };

  const setFavorite = async (data, flag) => {
    await setFavorites(
      data,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          alert("성공적으로 수행되었습니다.");
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
          let { data } = response;
          favorites.value = data;
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
        if (response.status === httpStatusCode.CREATE) {
          let { data } = response;
          let accessToken = data["accessToken"];
          let refreshToken = data["refreshToken"];
          isLogin.value = true;
          isLoginError.value = false;
          isValidToken.value = true;
          cookies.set("accessToken", accessToken, 3600);
          cookies.set("refreshToken", refreshToken);
          makeUserIdCookieStore(cookies.get("accessToken"));
          router.push("/");
        } else {
          isLogin.value = false;
          isLoginError.value = true;
          isValidToken.value = false;
        }
      },
      (error) => {
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
          alert("유저 정보가 없습니다.");
        }
      },
      (error) => {
        alert("아이디와 비밀번호를 확인해주세요 !");
      }
    );
  };

  const userDeleteStore = async (userId) => {
    await withdraw(
      userId,
      (response) => {
        if (response.status === httpStatusCode.NOCONTENT) {
          isLogin.value = false;
          cookies.remove("userId");
          cookies.remove("accessToken");
          cookies.remove("refreshToken");
        } else {
          console.error("유저 정보 없음 !!!");
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const makeUserIdCookieStore = (token) => {
    let decodeToken = jwtDecode(token);
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
          console.error("Error status : ", response.status);
        }
      },
      (error) => {
        console.error(error);
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
    setmyStorageHotPlace,
    getmyStorageHotPlace,
  };
});
