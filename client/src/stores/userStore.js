import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import { join, login, logout, update, withdraw, info, tokenRegeneration } from "@/api/userApi";
import { httpStatusCode } from "@/util/http-status";

// cookies
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

export const useUserStore = defineStore("userStore", () => {
  const router = useRouter();

  const isLogin = ref(false);
  const isLoginError = ref(false);
  const userInfo = ref(null);
  const isValidToken = ref(false);

  const userLoginStore = async (loginUser) => {
    await login(
      loginUser,
      (response) => {
        // console.log("login ok!!!!", response.status);
        // console.log("login ok!!!!", httpStatusCode.CREATE);
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
          cookies.set("accessToken", accessToken, 600);
          cookies.set("refreshToken", refreshToken);

        } else {
          console.log("로그인 실패했다");
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

    // console.log(userInfo.value);
    // console.log(userId);

    await logout(
      userId,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          isLogin.value = false;
          cookies.remove("userId")
          cookies.remove("accessToken");
          cookies.remove("refreshToken");
        } else {
          console.error("유저 정보 없음!!!!");
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const userDeleteStore = async (userId) => {

    console.log("userStore.userDeleteStore => userId : ", userId);

    await withdraw(
      userId,
      (response) => {
        if (response.status === httpStatusCode.NOCONTENT) {
          console.log("userStore.userDeleteStore => status : ", response.status);
          isLogin.value = false;
          cookies.remove("userId")
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
    cookies.set("userId", decodeToken.userId, 600);
  }

  // const getUserInfo = async (userId) => {
  //   info(
  //     userId,
  //     (response) => {
  //       if (response.status === httpStatusCode.OK) {
  //         // cookies.set("userId", response.data.userInfo.userId);
  //         console.log("3. getUserInfo data >> ", response.data.userInfo);
  //         userInfo.value = response.data.userInfo;
  //         console.log("userStore => userInfo : ", userInfo.value);
  //         // return response.data.userInfo;
  //       } else {
  //         console.log("유저 정보 없음!!!!");
  //       }
  //     },
  //     async (error) => {
  //       console.error(
  //         "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
  //         error.response.status
  //       );
  //       isValidToken.value = false;

  //       // await tokenRegenerate();
  //     }
  //   );
  // };

  // const tokenRegenerate = async () => {
  //   console.log("토큰 재발급 >> 기존 토큰 정보 : {}", sessionStorage.getItem("accessToken"));
  //   await tokenRegeneration(
  //     JSON.stringify(userInfo.value),
  //     (response) => {
  //       if (response.status === httpStatusCode.CREATE) {
  //         let accessToken = response.data["access-token"];
  //         console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
  //         sessionStorage.setItem("accessToken", accessToken);
  //         isValidToken.value = true;
  //       }
  //     },
  //     async (error) => {
  //       // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
  //       if (error.response.status === httpStatusCode.UNAUTHORIZED) {
  //         console.log("갱신 실패");
  //         // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
  //         await logout(
  //           userInfo.value.userid,
  //           (response) => {
  //             if (response.status === httpStatusCode.OK) {
  //               console.log("리프레시 토큰 제거 성공");
  //             } else {
  //               console.log("리프레시 토큰 제거 실패");
  //             }
  //             alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
  //             isLogin.value = false;
  //             userInfo.value = null;
  //             isValidToken.value = false;
  //             router.push({ name: "user-login" });
  //           },
  //           (error) => {
  //             console.error(error);
  //             isLogin.value = false;
  //             userInfo.value = null;
  //           }
  //         );
  //       }
  //     }
  //   );
  // };

  return {
    isLogin,
    isLoginError,
    userInfo,
    isValidToken,
    userLoginStore,
    userLogoutStore,
    userDeleteStore,
    // getUserInfo,
    makeUserIdCookieStore,
  };
});
