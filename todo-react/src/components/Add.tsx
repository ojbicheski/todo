import { Todo } from "model/Todo";
import { useState } from "react";
import valueForm from "utils/ValueForm";

interface TodoAddProps {
  add: (todo: Todo) => void;
}

export function Add( { add }: TodoAddProps ) {
  const [content, setContent] = useState('');

  const handleChange = (event: any) => {
    const change = valueForm(event);
    setContent(change);
  }

  const handleAdd = () => {
    let todo = new Todo();
    todo.content = content;
    add(todo);
    setContent('');
  }

  return (
    <div>
      <input name="content"
        onChange={handleChange}
        value={content}
      />
      <button className="add-todo" onClick={handleAdd}>
        Add Todo
      </button>
    </div>
  );
}