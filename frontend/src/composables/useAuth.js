import {useRouter} from "vue-router";
import {useApi} from "./useApi.js";

export function useAuth() {
  const api = useApi();
  const router = useRouter();

  async function check() {
    const token = sessionStorage.getItem("token");
    if (token === null) {
      await router.push("/unauthorised");
      return {authorized: false, token: null, error: null};
    }

    try {
      const result = await api.checkUser(token);
      if (result.status === 403) {
        await router.push("/unauthorised");
        return {authorized: false, token, error: null};
      }
      return {authorized: true, token, error: null};
    } catch (error) {
      await router.push("/unauthorised");
      return {authorized: false, token, error};
    }
  }

  return {check};
}
