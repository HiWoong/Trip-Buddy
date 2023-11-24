<script setup>
import { ref, onMounted } from "vue";

import { useUserStore } from "@/stores/userStore.js";

import { updateVisitedCount, addHitCount, minHitCount } from "@/api/hotplaceApi.js";

import { httpStatusCode } from "@/util/http-status";

import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

const isClicked = ref(false);

const userStore = useUserStore();
const { setFavorite } = userStore;

const props = defineProps({
  hotPlace: Object,
  myFav: Object,
});
const userId = cookies.get("userId");
// 현재 핫플레이스를 사용자 찜 목록에 저장
// 사용자 테이블에 자신이 좋아요 한 게시물의 id들이 저장되어 있고,
// 그거를 pinia로 가지고 와서 관리하면서 만약 이 게시물이 로딩될 때 목록
// 안에 있다면 isLoved는 true, 아니면 false
const isLoved = ref(false);
onMounted(() => {
  if (props.myFav.includes(props.hotPlace.hotplaceId)) {
    isLoved.value = true;
  }
});

const takePlace = async () => {
  if (userId == null || userId == "") alert("좋아요 기능은 로그인 후에 이용하실 수 있습니다.");
  else {
    if (!isLoved.value) {
      const abc = props.myFav;
      abc.push(props.hotPlace.hotplaceId);
      await setFavorite(
        {
          userId: userId,
          favorite: JSON.stringify(abc),
        },
        true
      );
      await addLikeCount({ hotplaceId: props.hotPlace.hotplaceId });
      isLoved.value = true;
    } else {
      const abc = props.myFav;
      for (let i = 0; i < abc.length; i++) {
        if (abc[i] == props.hotPlace.hotplaceId) {
          abc.splice(i, 1);
          i--;
        }
      }

      await setFavorite(
        {
          userId: userId,
          favorite: JSON.stringify(abc),
        },
        false
      );
      await minLikeCount({ hotplaceId: props.hotPlace.hotplaceId });
      isLoved.value = false;
    }
  }
};

const click = async () => {
  isClicked.value = !isClicked.value;
  if (isClicked.value) {
    await changeVisitedCount({ hotplaceId: props.hotPlace.hotplaceId });
  }
};

const changeVisitedCount = async (hotPlaceDto) => {
  updateVisitedCount(
    hotPlaceDto,
    (response) => {
      if (response.status == httpStatusCode.OK) {
        props.hotPlace.visitedCount++;
      }
    },
    (error) => {
      console.error(error);
    }
  );
};

const addLikeCount = async (hotPlaceDto) => {
  addHitCount(
    hotPlaceDto,
    (response) => {
      if (response.status == httpStatusCode.OK) {
        props.hotPlace.hitCount++;
      }
    },
    (error) => {
      console.error(error);
    }
  );
};

const minLikeCount = async (hotPlaceDto) => {
  minHitCount(
    hotPlaceDto,
    (response) => {
      if (response.status == httpStatusCode.OK) {
        props.hotPlace.hitCount--;
      }
    },
    (error) => {
      console.error(error);
    }
  );
};
</script>
<template>
  <div class="wholeContent">
    <div>
      <div id="icon">
        <button id="favorite" @click="takePlace">
          <span v-if="isLoved">❤️</span>
          <span v-else>🤍</span>
        </button>
      </div>
      <img
        id="mainImage"
        :src="props.hotPlace.image"
        @click="click"
        style="width: 250px; height: 300px; border-radius: 2vh"
      />
    </div>
    <div class="placeDesc" @click="click">
      <div>
        <div class="placeVisit">{{ props.hotPlace.visitedCount }}</div>
        <div class="placeHit">{{ props.hotPlace.hitCount }}</div>
        <div v-show="isClicked">
          <div class="placeTitle">{{ props.hotPlace.subject }}</div>
          <div class="placeContent">
            {{ props.hotPlace.content }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
@font-face {
  font-family: "NanumSquare";
  src: url("../../assets/fonts/NanumSquareR.ttf") format("truetype");
}
@font-face {
  font-family: "NanumSquareB";
  src: url("../../assets/fonts/NanumSquareB.ttf") format("truetype");
}
* {
  margin: 0;
  padding: 0;
  font-family: "NanumSquare";
}
.wholeContent {
  position: relative;
  cursor: pointer;
}
.placeDesc {
  position: absolute;
  top: 0;
  width: 100%;
}
.placeVisit {
  background-color: transparent;
  margin: 11px 0 5px 10px;
  color: #000000;
  font-size: 15px;
  font-weight: 800;
}
.placeHit {
  margin: 0 19px 60px 0;
  text-align: end;
  color: #fa6464;
  font-size: 15px;
  font-weight: 600;
}
.placeTitle {
  font-size: 25px;
  font-weight: bold;
  color: whitesmoke;
  font-family: "NanumSquareB";
  margin-left: 10px;
}
.placeContent {
  word-break: break-all;
  width: 230px;
  height: 120px;
  font-size: 15px;
  color: #ffffff;
  border-radius: 15px;
  padding: 5px;
  margin-left: 10px;
  font-weight: 600;
  overflow-y: auto;
}
.placeContent::-webkit-scrollbar {
  width: 4px;
}
.placeContent::-webkit-scrollbar-thumb {
  height: 10%;
  border-radius: 15px;
}
#mainImage {
  position: relative;
}
#icon {
  display: flex;
  justify-content: end;
}
#favorite {
  position: absolute;
  z-index: 52;
  border: none;
  background-color: transparent;
  display: flex;
  justify-content: end;
}
span {
  font-size: 20px;
  margin: 10px 10px 0 0;
}
</style>
