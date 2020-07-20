import fetch from 'unfetch';

export const getPermission = (requesterId, requestingId) => fetch("http://localhost:8080/api/permissions/"+requesterId+"/"+requestingId);

