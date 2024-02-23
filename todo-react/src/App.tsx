import { Filter } from 'components/Filter';
import './App.css';
import { Add } from 'components/Add';
import { List } from 'components/List';
import { Todo } from 'model/Todo';
import { useEffect, useState } from 'react';
import { VISIBILITY_FILTERS } from 'utils/constants';
import { todoApi } from 'services/todoApi';

function App() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | undefined>(undefined);
  const [todos, setTodos] = useState<Todo[]>([]);
  const [filter, setFilter] = useState<any>(VISIBILITY_FILTERS.ALL);
  const [current, setCurrent] = useState<Todo | undefined>(undefined);
  
  useEffect(() => {
    async function load() {
      setLoading(true);
      try {
        const data = await todoApi.get();
        switch (filter) {
          case VISIBILITY_FILTERS.INCOMPLETE:
            setTodos(data.filter((t) => !t.completed));
            break;
          case VISIBILITY_FILTERS.COMPLETED:
            setTodos(data.filter((t) => t.completed));
            break;
          default:
            setTodos(data);
        } 
      } catch (e) {
        if (e instanceof Error) {
          setError(e.message);
        }
      } finally {
        setLoading(false);
      }
    }
    load();
  }, [filter, current]);
  
  function toggle(todo: Todo) {
    todo.completed = !todo.completed;
    todoApi.put(todo).then((updatedTodo) => {
      setCurrent(updatedTodo);
    });
  }

  function add(todo: Todo) {
    todoApi.post(todo).then((newTodo) => {
      setCurrent(newTodo);
    });
  }
      
  return (
    <div className="todo-app">
    <h1>Todo List</h1>
    { error && (
      <>
        ERROR: { error }
      </>
    )}

    { loading && (
      <>
        Loading...
      </>
    )}

    { !error && !loading && (
      <>
        <Add add={ add } />
        <List todos={ todos } toggle={ toggle }/>
        <Filter activeFilter={ filter } setFilter={ setFilter } />
      </>
    )}
  </div>
);
}

export default App;
