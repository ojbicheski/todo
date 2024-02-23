import { Todo } from "model/Todo";
import { Item } from "components/Item";

interface TodoListProps {
  todos: Todo[];
  toggle: (todo: Todo) => void;
}

export function List({ todos: initial, toggle }: TodoListProps) {
  return (
    <ul className="todo-list">
      { initial && initial.length
        ? initial.map((todo, index) => {
            return <Item key={`todo-${todo.id}`} todo={todo} toggle={toggle}/>;
          })
        : "No todos, yay!"
      }
    </ul>
  );
}