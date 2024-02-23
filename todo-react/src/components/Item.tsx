import { Todo } from "model/Todo";
import cx from "classnames";

interface TodoItemProps {
  todo: Todo;
  toggle: (todo: Todo) => void;
}

export function Item({ todo: initial, toggle }: TodoItemProps) {
  return (
    <li className="todo-item" onClick={() => toggle(initial)}>
      {initial && initial.completed ? "👌" : "👋"}{" "}
      <span
        className={cx(
          "todo-item__text",
          initial && initial.completed && "todo-item__text--completed"
        )}
      >
        {initial.content}
      </span>
    </li>
  );
}