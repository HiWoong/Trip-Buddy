import axios from "axios";
export default axios.create({
  // 만약 안되면 환경변수 파일에서 "url" 가져오면 됨
  baseURL: import.meta.env.VITE_BASE_URL,
});
