const valueForm = (event: any) => {
  const { type, name, value, checked } = event.target;
  // if input type is checkbox use checked
  // otherwise it's type is text, number etc. so use value
  let updatedValue = type === 'checkbox' ? checked : value;

  //if input type is number convert the updatedValue string to a +number
  if (type === 'number') {
    updatedValue = Number(updatedValue);
  }
  return updatedValue;  
  // return {
  //   [name]: updatedValue,
  // };
}

export default valueForm;