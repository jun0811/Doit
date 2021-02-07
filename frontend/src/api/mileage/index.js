import http from '../../http-common';

function createProduct(params, success, fail) {
  http
  .post(`product/createProduct`, params)
  .then(success)
  .catch(fail);
} 

export { createProduct }