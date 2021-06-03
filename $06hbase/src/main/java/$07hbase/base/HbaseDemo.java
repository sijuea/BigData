package $07hbase.base;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.phoenix.queryserver.client.ThinClientUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HbaseDemo {
    private Connection connection;
    private Admin admin;

    @Before
    public void init() throws Exception{
        //1、获取connection
        Configuration conf = HBaseConfiguration.create();

        //配置zookeeper地址
        conf.set("hbase.zookeeper.quorum","hadoop102:2181,hadoop103:2181,hadoop104:2181");
        connection = ConnectionFactory.createConnection(conf);
        //2、获取admin对象
        admin = connection.getAdmin();
    }

    @After
    public void close() throws Exception{
        //5、关闭
        if(admin!=null)
            admin.close();
        if(connection!=null)
            connection.close();
    }
    /**
     * 创建命名空间
     */
    @Test
    public void createNamespace() throws IOException {


        //3、封装数据
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create("big").build();
        //4、创建
        admin.createNamespace(namespaceDescriptor);

    }

    /**
     * 查看所有的命名空间
     * @throws Exception
     */
    @Test
    public void listNamespace() throws Exception{

        NamespaceDescriptor[] namespaces = admin.listNamespaceDescriptors();

        for(NamespaceDescriptor namespace: namespaces){
            System.out.println(namespace.getName());
        }
    }

    /**
     * 查看命名空间下所有表
     * @throws Exception
     */
    @Test
    public void listNamespaceTables() throws Exception{

        TableName[] tables = admin.listTableNamesByNamespace("hbase");

        for(TableName name: tables){
            System.out.println(name.getNameAsString());
        }
    }

    /**
     * 删除命名空间
     * @throws Exception
     */
    @Test
    public void dropNamespace() throws Exception{

        //获取命名空间下所有表
        TableName[] tables = admin.listTableNamesByNamespace("big");
        //删除命名空间下所有表
        for(TableName table: tables){
            //禁用表
            admin.disableTable(table);
            //删除表
            admin.deleteTable(table);
        }
        //删除命名空间
        admin.deleteNamespace("big");

       // admin.namespace

    }

    /**
     * 创建表
     * @throws Exception
     */
    @Test
    public void createTable() throws Exception{

        //封装列簇信息
        ColumnFamilyDescriptor f1 = ColumnFamilyDescriptorBuilder.newBuilder("f1".getBytes()).build();
        ColumnFamilyDescriptor f2 = ColumnFamilyDescriptorBuilder.newBuilder("f2".getBytes()).build();


        //封装表的信息
        TableName student = TableName.valueOf("bigdata:dog3");
        TableDescriptor descriptor = TableDescriptorBuilder.newBuilder(student)
                .setColumnFamily(f1)
                .setColumnFamily(f2)
                .build();
        //创建
        //admin.createTable(descriptor);
        //通过api创建表预分区
        //admin.createTable(descriptor,"1".getBytes(),"4".getBytes(),5);
        byte[][] splitkeys = { "11".getBytes(),"22".getBytes() };
        admin.createTable(descriptor,splitkeys);

    }

    /**
     * 修改表
     * @throws Exception
     */
    @Test
    public void alterTable() throws Exception{

        //封装列簇信息
        ColumnFamilyDescriptor f1 = ColumnFamilyDescriptorBuilder.newBuilder("f1".getBytes()).setMaxVersions(2).build();
        ColumnFamilyDescriptor f2 = ColumnFamilyDescriptorBuilder.newBuilder("f2".getBytes()).build();

        TableName student = TableName.valueOf("bigdata:dog");
        TableDescriptor descriptor = TableDescriptorBuilder.newBuilder(student)
                .setColumnFamily(f1)
                .setColumnFamily(f2)
                .build();

        admin.modifyTable(descriptor);


    }

    @Test
    public void put() throws Exception{

        //获取table对象
        Table table = connection.getTable(TableName.valueOf("bigdata:dog"));
        //封装数据
        //put 'bigdata:dog','1001'
        //put可以封装逻辑结构一整行数据
        Put put = new Put("1001".getBytes());
        put.addColumn("f1".getBytes(),"name".getBytes(),"zhangsan".getBytes());
        put.addColumn("f1".getBytes(),"age".getBytes(), Bytes.toBytes(20));
        put.addColumn("f1".getBytes(),"address".getBytes(), "shenzhen".getBytes());
        //插入
        table.put(put);
        //关闭
        table.close();
    }

    /**
     * 批量数据插入
     * @throws Exception
     */
    @Test
    public void putList() throws Exception{

        //获取table对象
        Table table = connection.getTable(TableName.valueOf("bigdata:dog"));

        //封装数据
        List<Put> lists = new ArrayList<Put>();
        Put put = null;

        for(int i=0;i<10;i++){
            put = new Put( ("100"+i).getBytes() );

            put.addColumn("f1".getBytes(),"name".getBytes(), ("zhangsan-"+i).getBytes() );
            put.addColumn("f1".getBytes(),"age".getBytes(), Bytes.toBytes(20+i) );

            lists.add(put);
        }
        //插入
        table.put(lists);

        //关闭
        table.close();
    }

    /**
     * 根据单个主键查询数据
     * @throws Exception
     */
    @Test
    public void get() throws Exception{

        //获取table
        Table table = connection.getTable(TableName.valueOf("bigdata:dog"));
        //封装数据
        Get get = new Get("1001".getBytes());
        //查询
        Result result = table.get(get);
        //展示结果
        List<Cell> cells = result.listCells();
        for (Cell cell: cells){
            //rowkey
            String rowkey = new String(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
            //列簇
            String family = new String(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
            //列限定符
            String qualifier = new String(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            //获取value值
            if(family.equals("f1") && qualifier.equals("age")){
                int value = Bytes.toInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                System.out.println("rowkey="+rowkey+",列簇="+family+",列限定符="+qualifier+",value="+value);

            }else{

                String value = new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                System.out.println("rowkey="+rowkey+",列簇="+family+",列限定符="+qualifier+",value="+value);
            }
        }
        //关闭
        table.close();
    }

    /**
     * 批量查询
     * @throws Exception
     */
    @Test
    public void getList() throws Exception{

        Table table = connection.getTable(TableName.valueOf("bigdata:dog"));

        List<Get> lists  = new ArrayList<Get>();
        Get get = null;
        for(int i=0;i<=5;i++){
            get = new Get( ("100"+i).getBytes() );
            //指定查询某个列簇的数据
            //get.addFamily("f1".getBytes());
            //指定查询某个列的数据
            //get.addColumn("f1".getBytes(),"name".getBytes());
            //指定查询多个版本的数据
            //get.readVersions(3);
            //指定查询某个时间戳的数据
            //get.setTimestamp(1617765045257L);
            //当前结果是否保存在读缓存中
            get.setCacheBlocks(true);
            lists.add(get);

        }
        Result[] results = table.get(lists);
        System.out.println(results.length);
        for(Result  result: results){
            List<Cell> cells = result.listCells();
            if(cells!=null){

                for (Cell cell: cells){
                    //rowkey
                    String rowkey = new String(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
                    //列簇
                    String family = new String(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
                    //列限定符
                    String qualifier = new String(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                    //获取value值
                    if(family.equals("f1") && qualifier.equals("age")){
                        int value = Bytes.toInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                        System.out.println("rowkey="+rowkey+",列簇="+family+",列限定符="+qualifier+",value="+value);

                    }else{

                        String value = new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                        System.out.println("rowkey="+rowkey+",列簇="+family+",列限定符="+qualifier+",value="+value);
                    }
                }
            }
        }

        table.close();
    }

    /**
     * 删除数据
     * @throws Exception
     */
    @Test
    public void delete() throws Exception{

        Table table = connection.getTable(TableName.valueOf("bigdata:dog"));
        Delete delete = new Delete("1002".getBytes());
        //删除单个cell
        //delete.addColumn("f1".getBytes(),"name".getBytes());
        table.delete(delete);

        //table.de

        table.close();


    }

    /**
     * 清空表
     * @throws Exception
     */
    @Test
    public void truncate() throws Exception{

        admin.disableTable(TableName.valueOf("bigdata:dog"));
        admin.truncateTable(TableName.valueOf("bigdata:dog"),false);
    }


    @Test
    public void scan() throws Exception{

        //获取table
        Table table = connection.getTable(TableName.valueOf("bigdata:dog"));
        //封装数据
        //查询整表数据
        Scan scan = new Scan();
        //查询某个列簇的数据
        //scan.addFamily("f1".getBytes());
        //查询某个列的数据
        //scan.addColumn("f1".getBytes(),"name".getBytes());
        //查询多个版本的数据
        //scan.readVersions(2);
        //查询rowkey范围段的数据
        //scan.withStartRow("1001".getBytes(),true);
        //scan.withStopRow("1003".getBytes(),true);
        //当前结果是否放入读缓存
        //scan.setCacheBlocks(true);
        //查询
        ResultScanner scanner = table.getScanner(scan);
        //结果展示
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()){
            Result row = iterator.next();
            List<Cell> cells = row.listCells();
            for(Cell cell: cells){
                //获取rowkey
                String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
                //获取列簇
                String family = Bytes.toString(CellUtil.cloneFamily(cell));
                //获取列限定符
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                //获取value值
                if(family.equals("f1") && qualifier.equals("age")){
                    int value = Bytes.toInt(CellUtil.cloneValue(cell));
                    System.out.println("rowkey="+rowkey+",列簇="+family+",列限定符="+qualifier+",value="+value);
                }else{
                    String value = Bytes.toString(CellUtil.cloneValue(cell));
                    System.out.println("rowkey="+rowkey+",列簇="+family+",列限定符="+qualifier+",value="+value);
                }
            }
        }
        //关闭
        table.close();
    }


    /**
     *  select * from dog where name='zhangsan-7'
     * @throws Exception
     */
    @Test
    public void filter() throws Exception{

        //获取table对象
        Table table = connection.getTable(TableName.valueOf("bigdata:dog"));
        //封装数据
        Scan scan = new Scan();
        //过滤 f1:name = 'zhangsan-7' 的数据
        //默认情况下,如果一行数据没有过滤的列,此时默认展示该数据
        SingleColumnValueFilter filter = new SingleColumnValueFilter("f1".getBytes(),"name".getBytes(),CompareOperator.EQUAL,"zhangsan-7".getBytes());
        //如果一行数据没有要过滤的列,则不展示数据
        filter.setFilterIfMissing(true);
        scan.setFilter(filter);
        //查询
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()){
            Result row = iterator.next();
            List<Cell> cells = row.listCells();
            for(Cell cell: cells){
                //获取rowkey
                String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
                //获取列簇
                String family = Bytes.toString(CellUtil.cloneFamily(cell));
                //获取列限定符
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                //获取value值
                if(family.equals("f1") && qualifier.equals("age")){
                    int value = Bytes.toInt(CellUtil.cloneValue(cell));
                    System.out.println("rowkey="+rowkey+",列簇="+family+",列限定符="+qualifier+",value="+value);
                }else{
                    String value = Bytes.toString(CellUtil.cloneValue(cell));
                    System.out.println("rowkey="+rowkey+",列簇="+family+",列限定符="+qualifier+",value="+value);
                }
            }
        }
        //关闭
        table.close();
    }

    /**
     * select * from dog where name like '%zhangsan%'
     * @throws Exception
     */
    @Test
    public void filter2() throws Exception{
        //获取table对象
        Table table = connection.getTable(TableName.valueOf("bigdata:dog"));
        //封装数据
        Scan scan = new Scan();
        //过滤 f1:name = 'zhangsan-7' 的数据
        //默认情况下,如果一行数据没有过滤的列,此时默认展示该数据
        SubstringComparator comparator = new SubstringComparator("zhangsan");
        SingleColumnValueFilter filter = new SingleColumnValueFilter("f1".getBytes(),"name".getBytes(), CompareOperator.EQUAL,comparator);

        scan.setFilter(filter);
        //查询
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()){
            Result row = iterator.next();
            List<Cell> cells = row.listCells();
            for(Cell cell: cells){
                //获取rowkey
                String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
                //获取列簇
                String family = Bytes.toString(CellUtil.cloneFamily(cell));
                //获取列限定符
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                //获取value值
                if(family.equals("f1") && qualifier.equals("age")){
                    int value = Bytes.toInt(CellUtil.cloneValue(cell));
                    System.out.println("rowkey="+rowkey+",列簇="+family+",列限定符="+qualifier+",value="+value);
                }else{
                    String value = Bytes.toString(CellUtil.cloneValue(cell));
                    System.out.println("rowkey="+rowkey+",列簇="+family+",列限定符="+qualifier+",value="+value);
                }
            }
        }
        //关闭
        table.close();
    }

    /**
     * select * from dog where name='zhangsan-7' or (name='zhangsan-5' and age=25)
     * @throws Exception
     */
    @Test
    public void filterList() throws Exception{
        //获取table对象
        Table table = connection.getTable(TableName.valueOf("bigdata:dog"));
        //封装数据
        Scan scan = new Scan();
        //name='zhangsan-7'
        SingleColumnValueFilter name1Filter = new SingleColumnValueFilter("f1".getBytes(), "name".getBytes(), CompareOperator.EQUAL, "zhangsan-7".getBytes());
        //name='zhangsan-5'
        SingleColumnValueFilter name2Filter = new SingleColumnValueFilter("f1".getBytes(), "name".getBytes(), CompareOperator.EQUAL, "zhangsan-5".getBytes());
        //age=25
        SingleColumnValueFilter ageFilter = new SingleColumnValueFilter("f1".getBytes(), "age".getBytes(), CompareOperator.EQUAL, Bytes.toBytes(25));

        //(name='zhangsan-5' and age=25)
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        filterList.addFilter(name2Filter);
        filterList.addFilter(ageFilter);

        //name='zhangsan-7' or (name='zhangsan-5' and age=25)
        FilterList filterList1 = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        filterList1.addFilter(name1Filter);
        filterList1.addFilter(filterList);
        scan.setFilter(filterList1);
        //查询
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()){
            Result row = iterator.next();
            List<Cell> cells = row.listCells();
            for(Cell cell: cells){
                //获取rowkey
                String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
                //获取列簇
                String family = Bytes.toString(CellUtil.cloneFamily(cell));
                //获取列限定符
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                //获取value值
                if(family.equals("f1") && qualifier.equals("age")){
                    int value = Bytes.toInt(CellUtil.cloneValue(cell));
                    System.out.println("rowkey="+rowkey+",列簇="+family+",列限定符="+qualifier+",value="+value);
                }else{
                    String value = Bytes.toString(CellUtil.cloneValue(cell));
                    System.out.println("rowkey="+rowkey+",列簇="+family+",列限定符="+qualifier+",value="+value);
                }
            }
        }
        //关闭
        table.close();
    }


    public static void main(String[] args) {
        String  s1=new String("22");
        String s2=new String("1625844");
        System.out.println(s1.compareTo(s2));
        Integer a1=22;
        Integer a2=12323444;
        System.out.println(a1.compareTo(a2));
        ThinClientUtil.getConnectionUrl("hadoop102", 8765);
    }
}
