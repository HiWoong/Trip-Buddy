import { ref } from "vue";
import { defineStore } from "pinia";

export const useMenuStore = defineStore("menuStore", () => {
  let menuFlag = ref("created_date");
  let flagName = ref("최신순");

  return {
    menuFlag,
    flagName,
  };
});