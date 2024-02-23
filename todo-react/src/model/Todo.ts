export class Todo {
  id: number | undefined;
  content: string = '';
  completed: boolean = false;

  constructor(initializer?: any) {
    if(!initializer) return;
    if(initializer.id) this.id = initializer.id; 
    if(initializer.content) this.content = initializer.content; 
    if(initializer.completed) this.completed = initializer.completed; 
  }
}