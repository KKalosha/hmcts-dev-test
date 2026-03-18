import { Application } from 'express';
import axios from 'axios';

export default function (app: Application): void {
  app.get('/', async (req, res) => {
    const now = new Date().toISOString().slice(0, 16);

    try {
      const response = await axios.get('http://localhost:4000/tasks');
      const tasks = response.data;

      res.render('home', { tasks, now });
    } catch (error) {
      console.error('Error making request:', error);

      res.render('home', {
        tasks: [],
        now,
      });
    }
  });

  app.post('/tasks', async (req, res) => {
    const { title, description, status, dueDate } = req.body;

    try {
      await axios.post('http://localhost:4000/tasks', {
        title,
        description,
        status,
        dueDate,
      });

      res.redirect('/');
    } catch (error) {
      console.error('Error creating task:', error);

      const now = new Date().toISOString().slice(0, 16);

      res.render('home', {
        tasks: [],
        now,
        errorMessage: 'Backend service is unavailable. Please ensure the backend server is running.',
      });
    }
  });

  app.post('/tasks/:id/delete', async (req, res) => {
    try {
      const { id } = req.params;

      await axios.delete(`http://localhost:4000/tasks/${id}`);

      res.redirect('/');
    } catch (error) {
      console.error('Error deleting task:', error);
      res.redirect('/');
    }
  });

  app.post('/tasks/:id/done', async (req, res) => {
    try {
      const { id } = req.params;

      await axios.patch(`http://localhost:4000/tasks/${id}/status`, {
        status: 'DONE',
      });

      res.redirect('/');
    } catch (error) {
      console.error('Error updating task status:', error);
      res.redirect('/');
    }
  });
}
