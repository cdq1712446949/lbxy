package com.lbxy.core;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;

/**
 * 在数据库表有任何变动时，运行一下 main 方法，极速响应变化进行代码重构
 */
public class Generator {

    static {
        PropKit.use("globalConfig.properties");
    }

    public static DataSource getDataSource() {
        DruidPlugin druidPlugin = Config.createDruidPlugin();
        druidPlugin.start();
        return druidPlugin.getDataSource();
    }

    public static void main(String[] args) {
        // base model 所使用的包名
        String baseModelPackageName = PropKit.get("jFinalConfig.modelGenerator.baseModelPackageName");
        // base model 文件保存路径
        String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/" + baseModelPackageName.replace('.', '/');

        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = PropKit.get("jFinalConfig.modelGenerator.modelPackageName");
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";

        // 创建生成器
        com.jfinal.plugin.activerecord.generator.Generator generator = new com.jfinal.plugin.activerecord.generator.Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        // 设置是否生成链式 setter 方法
        generator.setGenerateChainSetter(PropKit.getBoolean("jFinalConfig.modelGenerator.chainSetter", false));
        // 添加不需要生成的表名
        generator.addExcludedTable(PropKit.get("jFinalConfig.modelGenerator.excludedTable").split(","));
        // 设置是否在 Model 中生成 dao 对象
        generator.setGenerateDaoInModel(PropKit.getBoolean("jFinalConfig.modelGenerator.generateDaoInModel", true));
        // 设置是否生成字典文件
        generator.setGenerateDataDictionary(PropKit.getBoolean("jFinalConfig.modelGenerator.generateDataDictionary", false));
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
        generator.setRemovedTableNamePrefixes(PropKit.get("jFinalConfig.modelGenerator.removedTableNamePrefixes", ""));
        // 生成
        generator.generate();
    }
}




