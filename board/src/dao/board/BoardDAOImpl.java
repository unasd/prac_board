package dao.board;

import java.util.List;

import model.board.BoardModel;
import model.board.CommentModel;
import model.board.FileModel;

public interface BoardDAOImpl {
	public List<BoardModel> selectList(BoardModel boardModel);
	public int selectCount(BoardModel boardModel);
	public BoardModel select(BoardModel boardModel);
	public void insert(BoardModel boardModel);
	public void update(BoardModel boardModel);
	public void updateHit(BoardModel boardModel);
	public void delete(BoardModel boardModel);
	public void commentWrite(CommentModel commenModel);
	public List<CommentModel> commentSelect(CommentModel commentModel);
	public void commentDelete(CommentModel commentModel);
	public void fileUpload(FileModel fileModel);
	public List<FileModel> fileSelect(FileModel fileModel);
	public void fileDelete(FileModel fileModel);
}
