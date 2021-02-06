import axios from "axios";

const API_BASE_URL = "http://localhost:8080/group";

function createInstance(){
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers:{
      'Content-type': 'application/json',
    }
  });
  return instance
}

const instance = createInstance()

function createGroup(params,success,fail){
  instance
  .post(`createGroup`, params)
  .then(success)
  .catch(fail);
}
 

export { createGroup }