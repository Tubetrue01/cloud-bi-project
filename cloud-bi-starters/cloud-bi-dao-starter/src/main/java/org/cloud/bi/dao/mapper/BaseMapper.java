package org.cloud.bi.dao.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 描述：通用的 Mapper
 *
 * @author Tubetrue01@gmail.com by 2022/7/22
 */
@SuppressWarnings("unused")
public interface BaseMapper<ID, T> {

    /**
     * 保存对象信息
     *
     * @param t 需要保存的对象
     * @return 返回实际保存的条数
     */
    int insert(T t);

    /**
     * 根据对象列表进行保存
     *
     * @param param 需要保存的对象列表
     * @return 返回实际保存的条数
     */
    int insertByList(@Param("param") List<T> param);

    /**
     * 根据 Map 进行保存
     *
     * @param param 需要保存的对象 Map
     * @return 返回实际保存的条数
     */
    int insertByMap(@Param("param") Map<String, Object> param);

    /**
     * 根据对象进行删除
     *
     * @param t 需要删除的对象信息
     * @return 返回实际删除的对象条数
     */
    int delete(T t);

    /**
     * 根据主键进行删除
     *
     * @param id 需要删除的主键
     * @return 返回删除的条数
     */
    int deleteById(ID id);

    /**
     * 根据列表进行删除
     *
     * @param param 需要批量删除的对象列表
     * @return 返回实际删除的对象条数
     */
    int deleteByList(@Param("param") List<T> param);

    /**
     * 根据 Map 进行删除
     *
     * @param param 需要批量删除的对象 Map
     * @return 返回实际删除的对象条数
     */
    int deleteByMap(@Param("param") Map<String, Object> param);

    /**
     * 根据对象进行更新
     *
     * @param t 需要更新的对象信息
     * @return 返回成功更新的条数
     */
    int update(T t);

    /**
     * 根据列表进行更新
     *
     * @param param 需要更新的对象集合
     * @return 返回成功更新的条数
     */
    int updateByList(@Param("param") List<T> param);

    /**
     * 根据 Map 进行更新
     *
     * @param param 需要更新的 Map
     * @return 返回实际更新的条数
     */
    int updateByMap(@Param("param") Map<String, Object> param);

    /**
     * 查询所有条数
     *
     * @return 返回查询的集合
     */
    List<T> selectAll();

    /**
     * 根据对象进行查询
     *
     * @param t 需要查询的对象
     * @return 返回对像的详细信息
     */
    Optional<T> select(T t);

    /**
     * 根据主键进行查询
     *
     * @param id 根据主键进行查询
     * @return 返回对象的详细信息
     */
    Optional<T> selectById(ID id);

    /**
     * 根据对象进行查询
     *
     * @param t 根据对象进行查询
     * @return 返回该查询条件的列表
     */
    List<T> selectList(T t);

    /**
     * 查询分页信息
     *
     * @param param 根据 Map 进行查询
     * @return 返回分页信息
     */
    Page<T> selectListByPage(@Param("param") Map<String, Object> param);

    /**
     * 查询分页信息
     *
     * @param t 根据对象进行查询
     * @return 返回分页信息
     */
    Page<T> selectListByPage(T t);

    /**
     * 查询分页信息
     *
     * @param param 根据 Map 进行查询
     * @return 返回带有分页信息的 Map
     */
    Page<Map<String, Object>> selectMapByPage(@Param("param") Map<String, Object> param);

    /**
     * 根据 Map 进行查询
     *
     * @param param 根据 Map 进行查询
     * @return 返回满足条件的列表对象
     */
    List<T> selectListByMap(@Param("param") Map<String, Object> param);

    /**
     * 根据 id 集合批量查询
     *
     * @param idList 编号集合
     */
    List<T> selectListByIdList(@Param("idList") List<ID> idList);

    /**
     * 根据 Map 进行查询
     *
     * @param param 根据 Map 进行查询
     * @return 返回满足条件的 Map
     */
    Optional<Map<String, Object>> selectMapByMap(@Param("param") Map<String, Object> param);
}
