import { ref } from "vue";
import { defineStore } from "pinia";

export const useAttractionStore = defineStore("attractionStore", () => {
  const sidoCode = ref(0);
  const clickHome = ref(false);
  const isModalOpen = ref(false);

  const getIsModalOpen = () => {
    return isModalOpen.value;
  };

  const setIsModalOpen = () => {
    isModalOpen.value = !isModalOpen.value;
  };

  const setSidoCode = (num) => {
    sidoCode.value = num;
  };

  const getSidoCode = () => {
    return sidoCode.value;
  };

  const setTrueClickHome = () => {
    clickHome.value = true;
  };
  const setFalseClickHome = () => {
    clickHome.value = false;
  };

  const getClickHome = () => {
    return clickHome.value;
  };

  return {
    setSidoCode,
    getSidoCode,
    setTrueClickHome,
    setFalseClickHome,
    getClickHome,
    getIsModalOpen,
    setIsModalOpen,
  };
});
