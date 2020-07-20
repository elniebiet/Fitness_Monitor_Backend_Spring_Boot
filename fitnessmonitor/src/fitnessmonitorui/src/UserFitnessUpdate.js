import fetch from 'unfetch';

export const getFitnessUpdate = (userId) => fetch("http://localhost:8080/api/fitnessupdate/getupdate/"+userId);

