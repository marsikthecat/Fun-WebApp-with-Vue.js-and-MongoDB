import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

const authHeader = (token) => ({
  headers: {
    Authorization: token,
  },
});

export function useApi() {
  return {
    login(name, password) {
      return api.post("/users/login", {name, password});
    },
    adminLogin(name, password) {
      return api.post("/users/admin-login", {name, password});
    },
    register(name, password) {
      return api.post("/users/registering", {name, password});
    },
    checkUser(token) {
      return api.post("/users/checkUser", {}, authHeader(token));
    },
    logout(token) {
      return api.post("/users/logout", {}, authHeader(token));
    },
    getUsers(token) {
      return api.get("/users", authHeader(token));
    },
    getMessages(token) {
      return api.get("/message", authHeader(token));
    },
    sendMessage(message) {
      return api.post("/message/send", message);
    },
    getQuiz(token) {
      return api.get("/quiz", authHeader(token));
    },
    deleteQuizQuestion(questionID) {
      return api.delete("/quiz/deleteQuestion", {params: {questionID}});
    },
    storeQuizQuestion(question, correctIndex, options) {
      return api.post("/quiz/store", options, {
        params: {
          question,
          correctIndex,
        },
      });
    },
  };
}
