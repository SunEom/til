import http from 'http';
import { Server } from 'socket.io';
import express from 'express';
import { instrument } from '@socket.io/admin-ui';

const app = express();

app.set('view engine', 'pug');
app.set('views', __dirname + '/views');
app.use('/public', express.static(__dirname + '/public/js'));
app.get('/', (req, res) => res.render('home'));
app.get('/*', (req, res) => res.redirect('/'));

const handleListen = () => console.log(`Listening on http://localhost:3000`);

const httpServer = http.createServer(app);
const wsServer = new Server(httpServer, {
  cors: {
    origin: ['https://admin.socket.io'],
    credentials: true,
  },
});

wsServer.on('connection', (socket) => {
  socket.on('join_room', (roomName) => {
    socket.join(roomName);
    socket.to(roomName).emit('welcome');
  });

  socket.on('offer', (offer, roomName) => {
    socket.to(roomName).emit('offer', offer);
  });

  socket.on('answer', (answer, roomName) => {
    socket.to(roomName).emit('answer', answer);
  });

  socket.on('ice', (ice, roomName) => {
    socket.to(roomName).emit('ice', ice);
  });
});

instrument(wsServer, {
  auth: false,
});

httpServer.listen(3000, handleListen);
