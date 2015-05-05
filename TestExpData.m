
function TestExpData()
    function doTest(id, lambda)
        fid = fopen(sprintf('data%d.txt',id),'r'); 
        data = fscanf(fid, '%f,');
        fclose(fid);
        norm = exprnd(lambda,1000,1);
        disp(data);
        disp(norm);

        subplot(2,2,id);
        probplot('exp',[norm data]);
        legend('norm',sprintf('data%d',id), 'Location','NW');
        title(sprintf('Test #%d exponential distribution',id));
    end

    figure();
    doTest(1,100);
    doTest(2,1000);
    doTest(3,20);
end
