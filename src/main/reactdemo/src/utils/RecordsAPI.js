import axios from 'axios';
const api = process.env.REACT_APP_RECORDS_API_URL || "http://localhost:3001"

export const getAll = () =>
    axios.get(`${api}/records`);

export const create = (data) =>
    axios.post(`${api}/records`,data);

export const update = (id,data) =>
    axios.put(`${api}/records/${id}`,data);

export const remove = (id) =>
    axios.delete(`${api}/records/${id}`);