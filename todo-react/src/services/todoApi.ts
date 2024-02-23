import { Todo } from "model/Todo";

const baseUrl = process.env.REACT_APP_API_BASE_URL;
export const url = `${baseUrl}/api/todos`;

function checkStatus(response: Response) {
  if (response.ok) {
    return response;
  } else {
    const httpErrorInfo = {
      status: response.status,
      statusText: response.statusText,
      url: response.url,
    };
    console.log(`log server http error: ${JSON.stringify(httpErrorInfo)}`);

    throw new Error(JSON.stringify(response.body));
  }
}

function parseJSON(response: Response) {
  return response.json();
}

function convertToTodoModels(data: any[]): Todo[] {
  let todos: Todo[] = data.map(convertToTodoModel);
  return todos;
}

function convertToTodoModel(item: any): Todo {
  return new Todo(item);
}

const todoApi = {
  // get(page = 1, limit = 10) {
    // return fetch(`${url}?_page=${page}&_limit=${limit}&_sort=name`)
  get() {
    return fetch(`${url}`)
      .then(checkStatus)
      .then(parseJSON)
      .then(convertToTodoModels)
      .catch((error: TypeError) => {
        console.log('log client error ' + error);
        throw new Error(
          'There was an error retrieving the todos. Please try again.'
        );
      });
  },
  post(todo: Todo) {
    return fetch(`${url}`, {
      method: 'POST',
      body: JSON.stringify(todo),
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(checkStatus)
      .then(parseJSON)
      .then(convertToTodoModel)
      .catch((error: TypeError) => {
        console.log('log client error ' + error);
        throw new Error(
          'There was an error updating the todo. Please try again.'
        );
      });
  },
  put(todo: Todo) {
    return fetch(`${url}/${todo.id}`, {
      method: 'PUT',
      body: JSON.stringify(todo),
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(checkStatus)
      .then(parseJSON)
      .then(convertToTodoModel)
      .catch((error: TypeError) => {
        console.log('log client error ' + error);
        throw new Error(
          'There was an error updating the todo. Please try again.'
        );
      });
  },

};

export { todoApi };