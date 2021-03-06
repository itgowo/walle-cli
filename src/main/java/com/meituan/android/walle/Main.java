package com.meituan.android.walle;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.meituan.android.walle.WalleCommandLine;
import com.meituan.android.walle.commands.Batch2Command;
import com.meituan.android.walle.commands.IWalleCommand;
import com.meituan.android.walle.commands.RemoveCommand;
import com.meituan.android.walle.commands.ShowCommand;
import com.meituan.android.walle.commands.PutCommand;
import com.meituan.android.walle.commands.BatchCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chentong on 20/11/2016.
 */

public final class Main {
    private Main() {
        super();
    }

    public static void main(final String[] args) throws Exception {
        System.out.println("根据walle官方源码制作");
        System.out.println("walle源码：https://github.com/Meituan-Dianping/walle");
        System.out.println("主要是做了打包处理，增加了部分日志输出");
        System.out.println("有问题联系： Email：lujianchao@itgowo.com");
        System.out.println("有兴趣的看看我的其他项目： https://github.com/itgowo");
        final Map<String, IWalleCommand> subCommandList = new HashMap<String, IWalleCommand>();
        subCommandList.put("show", new ShowCommand());
        subCommandList.put("rm", new RemoveCommand());
        subCommandList.put("put", new PutCommand());
        subCommandList.put("batch", new BatchCommand());
        subCommandList.put("batch2", new Batch2Command());

        final WalleCommandLine walleCommandLine = new WalleCommandLine();
        final JCommander commander = new JCommander(walleCommandLine);

        for (Map.Entry<String, IWalleCommand> commandEntry : subCommandList.entrySet()) {
            commander.addCommand(commandEntry.getKey(), commandEntry.getValue());
        }
        try {
            commander.parse(args);
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            commander.usage();
            System.exit(1);
            return;
        }

        walleCommandLine.parse(commander);

        final String parseCommand = commander.getParsedCommand();
        if (parseCommand != null) {
            subCommandList.get(parseCommand).parse();
        }
    }
}
