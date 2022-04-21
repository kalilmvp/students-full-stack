import api from "../client";

const buildResponse = response => {
    if (response.status === 200) {
        return response;
    }

    const error = new Error(response.statusText);
    error.response = response;
    return Promise.reject(error);
}

export const getAllStudents = () => {
    return api.get("students")
        .then(buildResponse);
}

export const addNewStudent = student => {
    return api.post("students", student);
}
