import axios from "axios";
import cookie from "react-cookies";

const BASE_URL = 'http://localhost:8080/ThesisApp';

export const endpoints = {
  'login': '/api/login/',
  'current-user': '/api/current-user/',
  'thesis': '/api/thesis/',
  'thesis-details': (id) => `/api/thesis/${id}/`,
  'submit-report-file': (id) => `/api/thesis/${id}/submit-report-file/`,
  'change-password': '/api/change-password/',
  'majors': '/api/major/',
  'school-years': '/api/school-years/',
  'lecturers': '/api/lecturer/',
  'students': '/api/student/',
  'criterion': '/api/criterion/',
  'criterion-details': (id) => `/api/criterion/${id}/`,
  'council': '/api/council/',
  'council-details': (id) => `/api/council/${id}/`,
  'council-criterions': (id) => `/api/council/${id}/criterions/`,
  'submit-scores': (id) => `/api/council/${id}/mark/`,
  'council-marks': '/api/council/marks/',
  'block-council': (id) => `/api/council/${id}/block/`,
  'unblock-council': (id) => `/api/council/${id}/unblock/`,
  'stats-by-avg-thesis-scores': "/api/stats/avg-thesis-scores/",
  'stats-by-thesis-count-by-major': "/api/stats/thesis-count-by-major/",
  'change-avatar': '/api/change-avatar/',
  'thesis-report': (id) => `/api/thesis/${id}/report/`
}

export const authApi = () => {
  return axios.create({
    baseURL: BASE_URL,
    headers: {
      'Authorization': cookie.load('token')
    }
  })
}

export default axios.create({
  baseURL: BASE_URL
});