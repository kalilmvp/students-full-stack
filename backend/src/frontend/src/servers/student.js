import api from "../client";
import { errorNotification, warningNotification } from "../components/Notification";

const error400Status = [400, 404];

const buildResponse = response => {
    if (response.status === 200) {
        return response;
    }

    const errorResponse = response.response;
    const error = new Error(errorResponse.statusText);
    error.response = errorResponse;

    if (error400Status.includes(error.response.status)) {
        warningNotification(error.response.data.message, null, 'bottomLeft');
    } else {
        console.error(error);
        errorNotification('Internal Error', null, 'bottomLeft');
    }

    return Promise.reject(error);
}

export const getAllStudents = () => {
    return api.get("students")
        .then(buildResponse).catch(buildResponse);
}

export const addNewStudent = student => {
    return api.post("students", student).then(buildResponse).catch(buildResponse);
}

export const deleteStudent = id => {
    return api.delete(`students/${id}`).then(buildResponse).catch(buildResponse);
}
