package org.cloud.bi.dao.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import org.cloud.bi.dao.mapper.BaseMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 描述：通用 BaseService 类
 *
 * @author Tubetrue01@gmail.com by 2022/7/22
 */
@SuppressWarnings("unused")
public interface BaseService<ID, T> {

    /**
     * 需要子类实现该方法
     *
     * @return 返回具体的 Mapper 对象
     */
    BaseMapper<ID, T> getMapper();

    /**
     * 保存对象
     *
     * @param t 需要保存的对象
     * @return 返回保存的条数
     */
    default int save(T t) {
        return this.getMapper().insert(t);
    }

    /**
     * 批量保存对象
     *
     * @param param 需要保存的集合
     * @return 实际保存的条数
     */
    default int saveByList(List<T> param) {
        return this.getMapper().insertByList(param);
    }

    /**
     * 根据 Map 进行批量保存
     *
     * @param param 需要批量保存的 Map
     * @return 返回实际保存的对象信息
     */
    default int saveByMap(Map<String, Object> param) {
        return this.getMapper().insertByMap(param);
    }

    /**
     * 删除对象
     *
     * @param t 需要删除的对象
     * @return 返回实际删除的条数
     */
    default int remove(T t) {
        return this.getMapper().delete(t);
    }

    /**
     * 根据主键进行删除
     *
     * @param id 需要删除的主键
     * @return 返回实际删除的条数
     */
    default int removeById(ID id) {
        return this.getMapper().deleteById(id);
    }

    /**
     * 批量删除对象
     *
     * @param param 需要批量删除的对象集合
     * @return 返回实际删除的对象条数
     */
    default int removeByList(List<T> param) {
        return this.getMapper().deleteByList(param);
    }

    /**
     * 根据 Map 进行批量删除
     *
     * @param param 需要批量删除的对象 Map
     * @return 返回实际删除的条数
     */
    default int removeByMap(Map<String, Object> param) {
        return this.getMapper().deleteByMap(param);
    }

    /**
     * 根据对象进行修改
     *
     * @param t 需要更新的对象信息
     * @return 返回实际更新的对象条数
     */
    default int modify(T t) {
        return this.getMapper().update(t);
    }

    /**
     * 根据列表进行批量修改
     *
     * @param param 需要批量更新的对象集合
     * @return 返回实际更新的对象条数
     */
    default int modifyByList(List<T> param) {
        return this.getMapper().updateByList(param);
    }

    /**
     * 根据 Map 进行批量修改
     *
     * @param param 需要批量更新的 Map
     * @return 返回实际更新的对象条数
     */
    default int modifyByMap(Map<String, Object> param) {
        return this.getMapper().updateByMap(param);
    }

    /**
     * 获取指定的对象信息
     *
     * @param t 需要查询的对象
     * @return 对象的详细信息
     */
    default Optional<T> find(T t) {
        return this.getMapper().select(t);
    }

    /**
     * 查询所有对象
     *
     * @return 返回所有的对象集合
     */
    default List<T> findAll() {
        return this.getMapper().selectAll();
    }

    /**
     * 根据主键获取对象信息
     *
     * @param id 根据主键进行查询
     * @return 返回满足条件的对象信息
     */
    default Optional<T> findById(ID id) {
        return this.getMapper().selectById(id);
    }

    /**
     * 根据对象进行查询
     *
     * @param t 需要查询的对象
     * @return 满足该条件的对象信息
     */
    default List<T> findList(T t) {
        return this.getMapper().selectList(t);
    }

    /**
     * 查询分页信息
     *
     * @param pageNum  分页的页码
     * @param pageSize 每夜显示的条数
     * @param param    根据 Map 进行分页查询
     * @return 分页查询的结果集
     */
    default Page<T> findListByPage(int pageNum, int pageSize, Map<String, Object> param) {
        PageMethod.startPage(pageNum, pageSize);
        return this.getMapper().selectListByPage(param);
    }

    /**
     * 查询分页信息
     *
     * @param pageNum  分页的页码
     * @param pageSize 每夜显示的条数
     * @param param    根据 Map 进行查询
     * @return 返回分页封装的 Map 结果集
     */
    default Page<Map<String, Object>> findMapByPage(int pageNum, int pageSize, Map<String, Object> param) {
        PageMethod.startPage(pageNum, pageSize);
        return this.getMapper().selectMapByPage(param);
    }

    /**
     * 查询分页信息
     *
     * @param pageNum  分页的页码
     * @param pageSize 每夜显示的条数
     * @param t        根据对象行分页查询
     * @return 分页查询的结果集
     */
    default Page<T> findListByPage(int pageNum, int pageSize, T t) {
        PageMethod.startPage(pageNum, pageSize);
        return this.getMapper().selectListByPage(t);
    }

    /**
     * 获取对象集合
     *
     * @param param 根据 Map 进行查询
     * @return 返回满足该条件的列表对象
     */
    default List<T> findListByMap(Map<String, Object> param) {
        return this.getMapper().selectListByMap(param);
    }

    /**
     * 获取对象信息
     *
     * @param param 根据 Map 进行查询
     * @return 返回 Map 封装的结果集
     */
    default Optional<Map<String, Object>> findMapByMap(Map<String, Object> param) {
        return this.getMapper().selectMapByMap(param);
    }

}
