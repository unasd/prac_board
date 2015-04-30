package dao.board;

import java.util.List;

import model.board.BoardModel;
import model.board.CommentModel;
import model.board.FileModel;
import mybatis.MyBatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

@Repository("boardMyBatisDAO")
public class BoardMyBatisDAO implements BoardDAOImpl {
	private SqlSessionFactory sessionFactory = null;
	
	public BoardMyBatisDAO(){
		//System.out.println("boardmybatisdao create");
		this.sessionFactory = MyBatis.getSqlSessionFactory();
	}
	
	public List<BoardModel> selectList(BoardModel boardModel){
		SqlSession session = this.sessionFactory.openSession();
		try{
			//System.out.println("myBatis selectList");
			return session.selectList("board.selectList", boardModel);
		} catch(Exception err){
			err.printStackTrace();
		} finally{
			if(session!=null){
				session.close();
			}
		}
		return null;
	}

	@Override
	public int selectCount(BoardModel boardModel) {
		SqlSession session = this.sessionFactory.openSession();
		try{
			return session.selectOne("board.selectCount", boardModel);
		} catch(Exception err){
			err.printStackTrace();
		} finally {
			if(session!=null){
				session.close();
			}
		}
		return 0;
	}

	@Override
	public BoardModel select(BoardModel boardModel) {
		SqlSession session = this.sessionFactory.openSession();
		try{
			return session.selectOne("board.select", boardModel);
		} catch(Exception err){
			err.printStackTrace();
		} finally {
			if(session!=null){
				session.close();
			}
		}
		return null;
	}

	@Override
	public void insert(BoardModel boardModel) {
		SqlSession session = this.sessionFactory.openSession();
		try{
			session.insert("board.insert", boardModel);
		} catch(Exception err){
			err.printStackTrace();
		} finally {
			if(session!=null){
				session.close();
			}
		}
	}

	@Override
	public void update(BoardModel boardModel) {
		SqlSession session = this.sessionFactory.openSession();
		try{
			session.insert("board.update", boardModel);
		} catch(Exception err){
			err.printStackTrace();
		} finally {
			if(session!=null){
				session.close();
			}
		}
	}

	@Override
	public void updateHit(BoardModel boardModel) {
		SqlSession session = this.sessionFactory.openSession();
		try{
			session.update("board.updateHit", boardModel);
		} catch(Exception err){
			err.printStackTrace();
		} finally {
			if(session!=null){
				session.close();
			}
		}
	}

	@Override
	public void delete(BoardModel boardModel) {
		SqlSession session = this.sessionFactory.openSession();
		try{
			session.update("board.delete", boardModel);
		} catch(Exception err){
			err.printStackTrace();
		} finally {
			if(session!=null){
				session.close();
			}
		}
	}

	@Override
	public void commentWrite(CommentModel commentModel) {
		SqlSession session = this.sessionFactory.openSession();
		try{
			session.insert("board.commentWrite", commentModel);
			session.update("board.commentAdd", commentModel);
		} catch (Exception err){
			err.printStackTrace();
		} finally {
			if(session!=null){
				session.close();
			}
		}
	}

	@Override
	public List<CommentModel> commentSelect(CommentModel commentModel) {
		SqlSession session = this.sessionFactory.openSession();
		try{
			return session.selectList("board.commentSelect", commentModel);
		} catch (Exception err){
			err.printStackTrace();
		} finally {
			if(session!=null){
				session.close();
			}
		}
		return null;
	}

	@Override
	public void commentDelete(CommentModel commentModel) {
		SqlSession session = this.sessionFactory.openSession();
		try{
			session.delete("board.commentDelete", commentModel);
			session.update("board.commentDe", commentModel);
		} catch (Exception err){
			err.printStackTrace();
		} finally {
			if(session!=null){
				session.close();
			}
		}
	}

	@Override
	public void fileUpload(FileModel fileModel) {
		SqlSession session = this.sessionFactory.openSession();
		try{
			session.insert("board.fileUpload", fileModel);
		} catch (Exception err){
			err.printStackTrace();
		} finally {
			if(session!=null){
				session.close();
			}
		}
	}

	@Override
	public List<FileModel> fileSelect(FileModel fileModel) {
		SqlSession session = this.sessionFactory.openSession();
		try{
			//System.out.println("myBatis selectList");
			return session.selectList("board.fileSelect", fileModel);
		} catch(Exception err){
			err.printStackTrace();
		} finally{
			if(session!=null){
				session.close();
			}
		}
		return null;
	}

	@Override
	public void fileDelete(FileModel fileModel) {
		// TODO Auto-generated method stub
		
	}
}
